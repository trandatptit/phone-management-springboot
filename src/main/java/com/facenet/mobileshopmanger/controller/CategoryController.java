package com.facenet.mobileshopmanger.controller;

import com.facenet.mobileshopmanger.dto.response.ApiResponse;
import com.facenet.mobileshopmanger.dto.request.CategoryCreateRequest;
import com.facenet.mobileshopmanger.dto.response.CategoryResponse;
import com.facenet.mobileshopmanger.dto.response.PageResponse;
import com.facenet.mobileshopmanger.entity.Category;
import com.facenet.mobileshopmanger.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "https://phone-management-react.vercel.app/")
public class CategoryController {

    CategoryService categoryService;

    /**
     * Tạo mới một danh mục.
     *
     * @param categoryDTO đối tượng chứa thông tin của danh mục cần tạo
     * @return ApiResponse chứa thông tin về kết quả tạo mới
     */
    @PostMapping("/create")
    public ApiResponse<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest categoryDTO) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(categoryDTO))
                .build();
    }
    /**
     * Cập nhật thông tin danh mục.
     *
     * @param id          ID của danh mục cần cập nhật
     * @param categoryDTO đối tượng chứa thông tin mới của danh mục
     * @return ApiResponse chứa thông tin về kết quả cập nhật
     */
    @PutMapping("/update/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryCreateRequest categoryDTO) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(id, categoryDTO))
                .build();
    }

    /**
     * Xóa một danh mục theo ID.
     *
     * @param id ID của danh mục cần xóa
     * @return ApiResponse chứa thông tin về kết quả xóa
     */
    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ApiResponse.<String>builder()
                .result("Deleted category with id: " + id)
                .build();
    }

    /**
     * Khôi phục một danh mục đã bị xóa theo ID.
     *
     * @param id ID của danh mục cần khôi phục
     * @return ApiResponse chứa thông tin về kết quả khôi phục
     */
    @PutMapping("/restore/{id}")
    public ApiResponse<String> restoreCategory(@PathVariable Long id) {
        categoryService.restoreCategory(id);

        return ApiResponse.<String>builder()
                .result("Restored category with id: " + id)
                .build();
    }

    /**
     * Lấy danh sách tất cả các danh mục với phân trang.
     *
     * @param pageable đối tượng Pageable chứa thông tin phân trang
     * @return ApiResponse chứa danh sách các danh mục
     */
    @GetMapping("/all-page")
    public ApiResponse<PageResponse<CategoryResponse>> getAllCategories(Pageable pageable) {
        Page<CategoryResponse> categories = categoryService.getAllForPageCategories(pageable);

        PageResponse<CategoryResponse> pageResponse = PageResponse.<CategoryResponse>builder()
                .content(categories.getContent())
                .page(categories.getNumber())
                .size(categories.getSize())
                .totalElements(categories.getTotalElements())
                .totalPages(categories.getTotalPages())
                .last(categories.isLast())
                .build();

        return ApiResponse.<PageResponse<CategoryResponse>>builder()
                .result(pageResponse)
                .build();
    }

    /**
     * Lấy danh sách tất cả các danh mục.
     *
     * @return ApiResponse chứa danh sách các danh mục
     */
    @GetMapping("/all-list")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categories)
                .build();
    }

    /**
     * Lấy thông tin danh mục theo ID.
     *
     * @param id ID của danh mục cần lấy thông tin
     * @return ApiResponse chứa thông tin của danh mục
     */
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategoryById(id);
        return ApiResponse.<CategoryResponse>builder()
                .result(category)
                .build();
    }

    /**
     * Lấy danh sách tất cả các danh mục đã bị xóa với phân trang.
     *
     * @param pageable đối tượng Pageable chứa thông tin phân trang
     * @return ApiResponse chứa danh sách các danh mục đã bị xóa
     */
    @GetMapping("/deleted")
    public ApiResponse<PageResponse<CategoryResponse>> getAllDeletedCategories(Pageable pageable) {
        Page<CategoryResponse> deletedCategories = categoryService.getAllDeletedCategories(pageable);

        PageResponse<CategoryResponse> pageResponse = PageResponse.<CategoryResponse>builder()
                .content(deletedCategories.getContent())
                .page(deletedCategories.getNumber())
                .size(deletedCategories.getSize())
                .totalElements(deletedCategories.getTotalElements())
                .totalPages(deletedCategories.getTotalPages())
                .last(deletedCategories.isLast())
                .build();

        return ApiResponse.<PageResponse<CategoryResponse>>builder()
                .result(pageResponse)
                .build();
    }
}
