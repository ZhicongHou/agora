package com.hzcong.springboot.service.Impl;

import com.hzcong.data.jsonmsg.Message;
import com.hzcong.springboot.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private static String rootFileDirPath = "E:\\agora\\files";
    private static String rootTeaImgDirPath = "E:\\agora\\teaImg";
    private static String rootSecImgDirPath = "E:\\agora\\secImg";
//
//    private static String rootFileDirPath = "/home/scau2019/agora/files";
//    private static String rootTeaImgDirPath = "/home/scau2019/agora/teaImg";
//    private static String rootSecImgDirPath = "/home/scau2019/agora/secImg";

    private static double maxFileSize = 100.0;
    private static double maxRoomSize = 100.0;
    private static double btyeToM = 1024 * 1024;

    static {
        File rootFileDir = new File(rootFileDirPath);
        if (!rootFileDir.exists()) {
            rootFileDir.mkdirs();
        }

        File rootTeaImgDir = new File(rootTeaImgDirPath);
        if (!rootFileDir.exists()) {
            rootFileDir.mkdirs();
        }
    }


    @Override
    public double getSizeOfRoom(String secId) {
        String storeDirPath = rootFileDirPath + File.separator + secId;
        File storeDir = new File(storeDirPath);
        double total = 0;
        File[] files = storeDir.listFiles();
        if (files != null) {
            for (File file : files) {
                total += file.length();
            }
        }
        return total / btyeToM;
    }


    @Override
    public Message uploadFile(String secId, MultipartFile file) {
        if (!file.isEmpty()) {

            String fileName = file.getOriginalFilename();   //文件名
            String storeDirPath = rootFileDirPath + File.separator + secId;  //secId所对应的目录
            File storedFile = new File(storeDirPath, fileName);

            //如果secId对应的目录不存在，创建目录
            if (!storedFile.getParentFile().exists()) {
                storedFile.getParentFile().mkdirs();
            }

            //文件大小、容量限制
            double fileSize = 1.0 * file.getSize() / btyeToM;
            //判断文件大小是否大于100M的工作已经在前端执行了
            if (fileSize > maxFileSize) {
                return new Message("0", "上传失败，文件超出100M！");
            }
            if (fileSize + getSizeOfRoom(secId) > maxRoomSize) {
                return new Message("0g", "上传失败，房间剩余容量不足！");
            }

            //解决文件名重复问题
            StringBuilder firstName = new StringBuilder(fileName.lastIndexOf(".") == -1 ? fileName : fileName.substring(0, fileName.lastIndexOf(".")));
            String secondName = fileName.lastIndexOf(".") == -1 ? null : fileName.substring(fileName.lastIndexOf(".") + 1);
            while (storedFile.exists()) {
                firstName.append("-副本");
                fileName = firstName + (secondName == null ? "" : "." + secondName);
                storedFile = new File(storeDirPath, fileName);
            }

            //上传
            try {
                file.transferTo(storedFile);
            } catch (Exception e) {
                e.printStackTrace();
                return new Message("0", "上传失败！");
            }
            return new Message("0", "上传成功！");
        } else {
            return new Message("0", "上传失败！");
        }
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(String secId, String fileName) {
        String dirPath = rootFileDirPath + File.separator + secId;
        File file = new File(dirPath + File.separator + fileName);//新建一个文件对象
        HttpHeaders headers = new HttpHeaders();//http头信息
        String downloadFileName = null;//设置编码
        try {
            downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(downloadFileName);
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
        try {
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message deleteFile(String secId, String fileName) {
        String dirPath = rootFileDirPath + File.separator + secId;
        File file = new File(dirPath + File.separator + fileName);//新建一个文件对象
        if (!file.exists()) {
            return new Message("1", "文件不存在！");
        }
        file.delete();
        return new Message("0", "删除成功！");
    }

    public List<String> getFileList(String secId) {
        String dirPath = rootFileDirPath + File.separator + secId;
        File[] files = new File(dirPath).listFiles();
        List<String> fileList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                fileList.add(file.getName());
            }
        }
        return fileList;
    }


    @Override
    public Message uploadTeacherImage(String userName, MultipartFile image, String type) {
        String teaImgDirPath = rootTeaImgDirPath + File.separator + userName;
        File teaImgDir = new File(teaImgDirPath);
        if (!teaImgDir.exists()) {
            teaImgDir.mkdirs();
        }

//        String fileType = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        String imageName = type + ".jpg";
        String imagePath = teaImgDirPath + File.separator + imageName;
        File imageFile = new File(imagePath);
        try {
            image.transferTo(imageFile);// 把文件写入目标文件地址
        } catch (IOException e) {
            System.out.println("上传失败！");
            return new Message("1", "上传失败！");
        }
        System.out.println("上传成功！" + imageFile.length());
        return new Message("0", "上传成功！");
    }

    @Override
    public ResponseEntity<byte[]> downloadTeacherImage(String teaName, String type) {
        String teaImgDirPath = rootTeaImgDirPath + File.separator + teaName;
        File teaImgDir = new File(teaImgDirPath);
        if (!teaImgDir.exists()) {
            return null;
        }
        String fileName = type + ".jpg";

        String downloadFileName = null;
        try {
            downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        HttpHeaders headers = new HttpHeaders();//http头信息
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
        File file = new File(teaImgDir + File.separator + fileName);//新建一个文件对象
        try {
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Message uploadSectionImage(String teaName, MultipartFile image) {
        String secImgDirPath = rootSecImgDirPath;
        File secImgDir = new File(secImgDirPath);
        if (!secImgDir.exists()) {
            secImgDir.mkdirs();
        }

//        String fileType = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        String imageName = teaName + ".jpg";
        String imagePath = secImgDir + File.separator + imageName;
        File imageFile = new File(imagePath);
        try {
            image.transferTo(imageFile);// 把文件写入目标文件地址
        } catch (IOException e) {
            System.out.println("上传失败！");
            return new Message("1", "上传失败！");
        }
        System.out.println("上传成功！" + imageFile.length());
        return new Message("0", "上传成功！");
    }


    @Override
    public ResponseEntity<byte[]> downloadSectionImage(String secId) {
        String secImgDirPath = rootSecImgDirPath;
        File secImgDir = new File(secImgDirPath);
        if (!secImgDir.exists()) {
            return null;
        }
        String fileName = secId + ".jpg";

        String downloadFileName = null;
        try {
            downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        HttpHeaders headers = new HttpHeaders();//http头信息
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //MediaType:互联网媒介类型  contentType：具体请求中的媒体类型信息
        File file = new File(secImgDir + File.separator + fileName);//新建一个文件对象
        if (!file.exists()) {
            return null;
        }
        try {
            return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean renameSectionImage(String teaName, String secId) {
        String secImgDirPath = rootSecImgDirPath;
        File secImgDir = new File(secImgDirPath);
        if (!secImgDir.exists()) {
            secImgDir.mkdirs();
        }

//        String fileType = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        String imageName = teaName + ".jpg";
        String imagePath = secImgDir + File.separator + imageName;
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return false;
        }
        File newFile = new File(secImgDir + File.separator + secId + ".jpg");
        imageFile.renameTo(newFile);
        return true;
    }


}
