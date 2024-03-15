package org.zerock.mallapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDTO {

    private Long pno;

    private String pname;

    private int price;

    private String pdesc;

    private boolean delFlag;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>(); //등록이나 수정처럼 브라우저에서 보내주는 진짜 파일 데이터
    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>(); //DB에 저장되는 파일 데이터

}
