package com.jis.springbootjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class FileItem {

    private Long id;

    private String itemName;
    private UploadFile uploadFile;
    private List<UploadFile> uploadFiles;

}
