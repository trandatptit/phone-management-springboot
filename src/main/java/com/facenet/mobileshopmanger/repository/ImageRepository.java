package com.facenet.mobileshopmanger.repository;

import com.facenet.mobileshopmanger.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByPhoneId(Long phoneId);
}
