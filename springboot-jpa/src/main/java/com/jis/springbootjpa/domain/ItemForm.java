package com.jis.springbootjpa.domain;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ItemForm {

    private Long itemId;
    @NotBlank
    private String itemName;
    private MultipartFile multipartFile;
    private List<MultipartFile> multipartFiles;

}
