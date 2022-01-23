package com.jis.springbootjpa.controller;


import com.jis.springbootjpa.domain.FileItem;
import com.jis.springbootjpa.domain.ItemForm;
import com.jis.springbootjpa.domain.UploadFile;
import com.jis.springbootjpa.domain.repository.FileItemRepository;
import com.jis.springbootjpa.sevice.FileStoreService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/fileUpload")
public class FileUploadController {

    //properties에 설정한 value
    @Value("${file.dir}")
    private String fileDir;

    private final FileStoreService fileStoreService;

    private final FileItemRepository fileItemRepository;

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

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm itemForm){
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm itemForm, RedirectAttributes redirectAttributes) throws IOException {

        UploadFile uploadFile = fileStoreService.storeFile(itemForm.getMultipartFile());
        List<UploadFile> uploadFileList =  fileStoreService.storeFile(itemForm.getMultipartFiles());

        FileItem fileItem = new FileItem();
        fileItem.setItemName(itemForm.getItemName());
        fileItem.setUploadFile(uploadFile);
        fileItem.setUploadFiles(uploadFileList);
        fileItemRepository.save(fileItem);

        redirectAttributes.addAttribute("itemId",fileItem.getId());

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/item/{id}")
    public String itemView(@PathVariable Long id, Model model){

        var fileItem = fileItemRepository.findById(id);
        model.addAttribute("item",fileItem);
        //return
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String fileName) throws MalformedURLException {
        //파일을 반환
        return new UrlResource("file" + fileStoreService.getFullPath(fileName));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long id) throws MalformedURLException {
        //findById getById
        FileItem item = fileItemRepository.getById(id);
        String storeFileName = item.getUploadFile().getFileKey();
        String realFileName = item.getUploadFile().getRealFileName();

        UrlResource resource = new UrlResource("file" + fileStoreService.getFullPath(storeFileName));

        log.info("uploadFileName = {}",realFileName);
        
        //인코딩 설정
        String encodeUploadFileName = UriUtils.encode(realFileName,StandardCharsets.UTF_8);
        //파일 다운로드를 위한 설정
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName +"\"";

        return ResponseEntity.ok()
             .header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition)
             .body(resource);
    }
}
