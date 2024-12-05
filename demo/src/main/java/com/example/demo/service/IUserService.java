package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

    List<User> findAll();


    User findById(Integer id);

}
