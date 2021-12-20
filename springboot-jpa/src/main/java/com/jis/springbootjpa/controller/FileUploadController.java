package com.jis.springbootjpa.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

    //properties에 설정한 value
    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/uploadForm")
    public String uploadForm(){
        return "upload-form";
    }

    @PostMapping("/uploadForm")
    public String newFile(HttpServletRequest request) throws IOException, ServletException {
        log.info("request {}" ,request);

        String fileName = request.getParameter("itemName");

        Collection<Part> partCollection = request.getParts();
        //log.info("parts {} ",partCollection);//part 전체 출력

        for (Part part : partCollection) {

            log.info("name {}",part.getName());

            for (String headerName : part.getHeaderNames()) {
                log.info("header {} {}" , headerName,part.getHeader(headerName));
            }

            log.info("submittedFileName={}" ,part.getSubmittedFileName());
            log.info("size={} " ,part.getSize());
            
            //데이터를 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body");

            if(StringUtils.hasText(part.getSubmittedFileName())){
                String filePath = fileDir + part.getSubmittedFileName();
                log.info("filePath={}",filePath);
                part.write(filePath);
            }

        }
        
        return "upload-form";
    }
}
