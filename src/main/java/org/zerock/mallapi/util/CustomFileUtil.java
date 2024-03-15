package org.zerock.mallapi.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {
    @Value("${org.zerock.upload.path}")
    private String uploadPath;
    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);
        if(!tempFolder.exists()) {
            tempFolder.mkdir();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("-------------------------------------");
        log.info(uploadPath);
    }
    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{

        if(files == null || files.isEmpty()){
            return null;
        }

        List<String> uploadNames = new ArrayList<>();

        for(MultipartFile multipartFilefile: files){

            String savedName = UUID.randomUUID().toString()+"_"+multipartFilefile.getOriginalFilename();

            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(multipartFilefile.getInputStream(),savePath);//원본 파일 업로드

                String contentType = multipartFilefile.getContentType(); //mime type

                //이미지 파일이라면 -> 썸네일의 대상
                if(contentType != null || contentType.startsWith("image")){

                    Path thumbnailPath = Paths.get(uploadPath, "s_" + savedName);

                    Thumbnails.of(savePath.toFile()).size(200,200).toFile(thumbnailPath.toFile());

                }

                uploadNames.add(savedName);


            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }//end for


        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {

        Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);

        if(!resource.exists()) {
            resource = new FileSystemResource(uploadPath+File.separator+"default.jpg");
        }

        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFiles(List<String> fileNames) {

        if (fileNames == null || fileNames.size() == 0) {
            return;
        }
        fileNames.forEach(fileName -> {

            //썸네일 삭제
            String thumbnailFileName = "s_"+fileName;

            Path thumnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumnailPath);
            }catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }
        });

    }
}
