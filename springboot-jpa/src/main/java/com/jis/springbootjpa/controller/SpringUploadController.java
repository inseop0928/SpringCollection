package com.jis.springbootjpa.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/springUpload")
public class SpringUploadController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping( "/upload")
    private String newFile(){
        return "upload-form";
    }

    @PostMapping( "/upload")
    private String saveFile(@RequestParam String itemName,
                            @RequestParam MultipartFile file,
                            HttpServletRequest request) throws IOException {

        log.info("request={}",request);
        log.info("itemName={}",itemName);
        log.info("multipartFile={}",file);

        if(!file.isEmpty()){
            String filePath = fileDir + file.getOriginalFilename();
            log.info("파일저장 filePath {} ",filePath);
            file.transferTo(new File(filePath));//파일저장
        }

        return "upload-form";
    }






}
