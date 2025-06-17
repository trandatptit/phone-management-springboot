package com.facenet.mobileshopmanger.repository;

import com.facenet.mobileshopmanger.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    /**
     * Tìm kiếm tất cả hình ảnh liên kết với một điện thoại cụ thể.
     *
     * @param phoneId ID của điện thoại
     * @return Danh sách các hình ảnh liên kết với điện thoại
     */
    List<Image> findByPhoneId(Long phoneId);
}
