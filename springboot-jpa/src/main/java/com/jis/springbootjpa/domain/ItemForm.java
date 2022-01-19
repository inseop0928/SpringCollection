package com.jis.springbootjpa.domain;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm {

    private Long itemId;
    private String itemName;
    private MultipartFile multipartFile;
    private List<MultipartFile> multipartFiles;

}
