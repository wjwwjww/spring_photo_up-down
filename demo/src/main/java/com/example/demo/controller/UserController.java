package com.example.demo.controller;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private UserDao userDao;
    @GetMapping("/all")
    public List<User> getAll() {
        return userDao.findAll();
    }
//@GetMapping("/all")
//public String getAllUsers(Model model) {
//    List<User> users = userDao.findAll();
//    model.addAttribute("users", users);
//    System.out.println(model);
//    return model.toString();
//}
    @Transactional
    @PostMapping("/submit")
    public ResponseEntity<String> add(@RequestBody User user, Errors errors) {
        if(userDao.findUserById(user.getId()) == null) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            userDao.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully created.");

        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this ID already exists.");

        }

    }

    @PostMapping("/updateUser")
    public ResponseEntity<String> updateUser(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") Integer id,

            HttpServletResponse httpServletResponse) {

        // 查找用户
        User user = userDao.findUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }



        // 处理文件上传逻辑
        if (!file.isEmpty()) {
            String filePath = saveFile(file);
            if (filePath != null) {
                user.setImage(filePath);  // 假设User有一个setImage方法来保存文件路径
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
            }
        }

        userDao.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully.");
    }

    private String saveFile(MultipartFile file) {
        // 处理文件保存的逻辑，例如保存到本地或云存储
        String fileName = file.getOriginalFilename();
        // 保存路径
        String path = "/Users/wangjiawei/StudioProjects/Android-SolarCulture/demo/src/main/resources/image/" + fileName;
        try {
            file.transferTo(new File(path)); // 保存文件
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 失败时返回null
        }
        return path; // 返回文件路径
    }

}
