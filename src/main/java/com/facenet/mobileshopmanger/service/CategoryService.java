package com.facenet.mobileshopmanger.service;

import com.facenet.mobileshopmanger.dto.request.CategoryCreateRequest;
import com.facenet.mobileshopmanger.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryCreateRequest categoryDTO);

    CategoryResponse updateCategory(Long id, CategoryCreateRequest categoryDTO);

    void deleteCategory(Long id);

    void restoreCategory(Long id);

    Page<CategoryResponse> getAllForPageCategories(Pageable pageable);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    Page<CategoryResponse> getAllDeletedCategories(Pageable pageable);
}
