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
    private List<MultipartFile> files = new ArrayList<>();
    @Builder.Default
    private List<String> uploadedFileNames = new ArrayList<>();


    public void setUploadFileNames(List<String> uploadFileNames) {
    }
}