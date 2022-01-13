package com.jis.springbootjpa.sevice;

import com.jis.springbootjpa.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStoreService {

    @Value("${file.dir}")
    private String fileDir;


    public String getFullPath(String fileName){
        return fileDir + fileName;
    }


    //uid+ 파일확장자 ex) sjfslkjasflkjfaslkjkfs.txt
    public String extFileName(String orginFileName){
        int position = orginFileName.lastIndexOf(".");
        String ext = orginFileName.substring(position+1);
        String _uid = UUID.randomUUID().toString();
        return _uid+"."+ext;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {

        if(!multipartFile.isEmpty()) {
            String orginFileName = multipartFile.getOriginalFilename();
            String fileKey = extFileName(orginFileName);
            multipartFile.transferTo(new File(getFullPath(fileKey)));

            UploadFile uploadFile = new UploadFile();
            uploadFile.setRealFileName(orginFileName);
            uploadFile.setFileKey(fileKey);
            return uploadFile;

        }else{
            return null;
        }
    }
}
