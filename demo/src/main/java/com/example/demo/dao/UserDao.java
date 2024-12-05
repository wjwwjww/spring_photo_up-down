package com.example.demo.dao;

import com.example.demo.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

public interface UserDao extends JpaRepository<User, Integer > {


    User findUserById(Integer id);

    @Modifying
    @Transactional // This is important for modifying queries
    @Query("UPDATE User u SET u.image = :path WHERE u.id = :id")
    void updateimage(@Param("id") Integer id, @Param("path") String path);

    @Query("SELECT u.image FROM User u WHERE u.id = :id")
    String getImageById(@Param("id") Integer id);
}
