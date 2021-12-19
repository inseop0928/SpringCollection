package com.jis.springbootjpa.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

    @GetMapping("/uploadForm")
    public String uploadForm(){
        return "upload-form";
    }



    @PostMapping("/uploadForm")
    public String newFile(HttpServletRequest request) throws IOException, ServletException {
        log.info("request {}" ,request);

        String fileName = request.getParameter("itemName");

        Collection<Part> partCollection = request.getParts();
        log.info("parts {} ",partCollection);

        return "upload-form";
    }
}
