package com.facenet.mobileshopmanger.service;

import com.facenet.mobileshopmanger.dto.request.CategoryCreateRequest;
import com.facenet.mobileshopmanger.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    /**
     * Tạo mới một danh mục.
     *
     * @param categoryDTO đối tượng chứa thông tin của danh mục cần tạo
     * @return đối tượng Category đã được lưu vào cơ sở dữ liệu
     */
    CategoryResponse createCategory(CategoryCreateRequest categoryDTO);


    /**
     * Cập nhật thông tin danh mục.
     *
     * @param id          ID của danh mục cần cập nhật
     * @param categoryDTO đối tượng chứa thông tin mới của danh mục
     * @return đối tượng Category đã được cập nhật
     */
    CategoryResponse updateCategory(Long id, CategoryCreateRequest categoryDTO);


    /**
     * Xóa danh mục theo ID.
     *
     * @param id ID của danh mục cần xóa
     */
    void deleteCategory(Long id);


    /**
     * Khôi phục danh mục đã xóa theo ID.
     *
     * @param id ID của danh mục cần khôi phục
     */
    void restoreCategory(Long id);

    /**
     * Lấy tất cả danh mục với phân trang.
     *
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách danh mục
     */
    Page<CategoryResponse> getAllForPageCategories(Pageable pageable);


    /**
     * Lấy tất cả danh mục.
     *
     * @return Danh sách các danh mục
     */
    List<CategoryResponse> getAllCategories();


    /**
     * Lấy danh mục theo ID.
     *
     * @param id ID của danh mục cần lấy
     * @return Danh mục tương ứng với ID
     */
    CategoryResponse getCategoryById(Long id);


    /**
     * Lấy tất cả danh mục đã xóa mềm với phân trang.
     *
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách danh mục đã xóa mềm
     */
    Page<CategoryResponse> getAllDeletedCategories(Pageable pageable);
}
