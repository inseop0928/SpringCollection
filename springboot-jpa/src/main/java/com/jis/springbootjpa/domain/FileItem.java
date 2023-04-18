package com.jis.springbootjpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class FileItem {

    private Long id;

    @NotBlank//빈값 + 공백 허용 X
    @NotNull//null값 허용 X
    private String itemName;

    @Range(min = 1000, max = 1000)
    private Integer price;

    private UploadFile uploadFile;
    private List<UploadFile> uploadFiles;

}
