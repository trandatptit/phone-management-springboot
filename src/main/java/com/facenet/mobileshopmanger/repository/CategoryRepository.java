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
    /**
     * Kiểm tra xem danh mục đã tồn tại với tên cụ thể hay chưa.
     *
     * @param name tên của danh mục
     * @return true nếu tồn tại, false nếu không
     */
    boolean existsByName(String name); // Kiểm tra xem danh mục đã tồn tại hay chưa


    /**
     * Tìm kiếm danh mục theo ID, bỏ qua các bản ghi đã xóa mềm.
     *
     * @param id ID của danh mục
     * @return Optional chứa danh mục nếu tìm thấy, hoặc rỗng nếu không tìm thấy
     */
    @Query(value = "SELECT * FROM categories WHERE id = :id", nativeQuery = true)
    Optional<Category> findByIdIgnoreSoftDeleted(@Param("id") Long id);


    /**
     * Tìm kiếm tất cả danh mục đã xóa mềm với phân trang.
     *
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách danh mục đã xóa mềm
     */
    @Query(
            value = "SELECT * FROM categories WHERE is_deleted = true",
            countQuery = "SELECT COUNT(*) FROM categories WHERE is_deleted = true",
            nativeQuery = true
    )
    Page<Category> findAllDeletedCategories(Pageable pageable);
}
