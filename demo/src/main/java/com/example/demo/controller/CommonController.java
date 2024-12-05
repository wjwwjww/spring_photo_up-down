package com.example.demo.controller;

import com.example.demo.common.CommonDTO;
import com.example.demo.dao.UserDao;


import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private final UserDao userDao;
    @Value("${images.path}")
    private String basePath;

    public CommonController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/upload")
    public CommonDTO<String> upload(MultipartFile file,Integer id){
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(basePath);

        if(!dir.exists()){
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+fileName));
            String filePath = basePath + fileName;
            System.out.println(filePath);
            System.out.println(id);
            userDao.updateimage(id, filePath);
        }catch (IOException e){
            e.printStackTrace();
        }
        CommonDTO<String> commonDTO = new CommonDTO<>();
        commonDTO.setContent(fileName);
        return commonDTO;
    }




//    @GetMapping("/download")
//    public void download(String fileName, HttpServletResponse response, OutputStream outputStream){
//        try {
//          FileInputStream fileInputStream=  new FileInputStream(new File(basePath+fileName));
//           ServletOutputStream OutputStream =response.getOutputStream();
//           response.setContentType("image/jpeg");
//           int len=0;
//           byte[] buffer = new byte[1024];
//           while ((len = fileInputStream.read(buffer))!=-1){
//               outputStream.write(buffer,0,len);
//               outputStream.flush();
//           }
//           outputStream.close();
//           fileInputStream.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
@GetMapping("/download")
public void download(Integer id, HttpServletResponse response){
    try {
      String fileName=  userDao.getImageById(id);
        File file = new File(fileName);


      System.out.println(fileName);
        FileInputStream fileInputStream=  new FileInputStream(new File(fileName));
        ServletOutputStream outputStream =response.getOutputStream();
        response.setContentType("image/jpeg");
        int len=0;
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }
        outputStream.close();
        fileInputStream.close();
    }catch (Exception e){
        e.printStackTrace();
    }
}
}
