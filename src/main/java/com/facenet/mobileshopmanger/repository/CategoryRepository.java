package com.facenet.mobileshopmanger.repository;

import com.facenet.mobileshopmanger.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name); // Kiểm tra xem danh mục đã tồn tại hay chưa


    @Query(value = "SELECT * FROM categories WHERE id = :id", nativeQuery = true)
    Optional<Category> findByIdIgnoreSoftDeleted(@Param("id") Long id); // Tìm kiếm danh mục theo ID, bỏ qua các bản ghi đã xóa mềm

    @Query(
            value = "SELECT * FROM categories WHERE is_deleted = true",
            countQuery = "SELECT COUNT(*) FROM categories WHERE is_deleted = true",
            nativeQuery = true
    )
    Page<Category> findAllDeletedCategories(Pageable pageable);
}
