package com.hzcong.springboot.service;


import com.hzcong.data.jsonmsg.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {



    double getSizeOfRoom(String secId);

    List<String> getFileList(String secId);

    Message uploadFile(String secId, MultipartFile file);

    ResponseEntity<byte[]> downloadFile(String secId, String fileName);

    Message deleteFile(String secId, String fileName);


    Message uploadTeacherImage(String userName, MultipartFile image, String type);

    ResponseEntity<byte[]> downloadTeacherImage(String teaName, String type);


    Message uploadSectionImage(String teaName, MultipartFile image);

    ResponseEntity<byte[]> downloadSectionImage(String secId);


    boolean renameSectionImage(String teaName, String secId);
}
