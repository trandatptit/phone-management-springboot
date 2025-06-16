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
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("/create")
    public ApiResponse<CategoryResponse> createCategory(@Valid @RequestBody CategoryCreateRequest categoryDTO) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(categoryDTO))
                .build();
    }
    @PutMapping("/update/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryCreateRequest categoryDTO) {
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(id, categoryDTO))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ApiResponse.<String>builder()
                .result("Deleted category with id: " + id)
                .build();
    }

    @PutMapping("/restore/{id}")
    public ApiResponse<String> restoreCategory(@PathVariable Long id) {
        categoryService.restoreCategory(id);

        return ApiResponse.<String>builder()
                .result("Restored category with id: " + id)
                .build();
    }

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

    @GetMapping("/all-list")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categories)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategoryById(id);
        return ApiResponse.<CategoryResponse>builder()
                .result(category)
                .build();
    }
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
