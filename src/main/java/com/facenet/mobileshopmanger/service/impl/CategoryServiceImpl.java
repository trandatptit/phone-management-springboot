package com.facenet.mobileshopmanger.service.impl;

import com.facenet.mobileshopmanger.dto.request.CategoryCreateRequest;
import com.facenet.mobileshopmanger.dto.response.CategoryResponse;
import com.facenet.mobileshopmanger.entity.Category;
import com.facenet.mobileshopmanger.exception.AppException;
import com.facenet.mobileshopmanger.exception.ErrorCode;
import com.facenet.mobileshopmanger.mapper.CategoryMapper;
import com.facenet.mobileshopmanger.repository.CategoryRepository;
import com.facenet.mobileshopmanger.service.CategoryService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    private static final Logger log = LogManager.getLogger();
    /**
     * Tạo mới một danh mục.
     *
     * @param categoryDTO đối tượng chứa thông tin của danh mục cần tạo
     * @return đối tượng Category đã được lưu vào cơ sở dữ liệu
     */
    @Override
    public CategoryResponse createCategory(CategoryCreateRequest categoryDTO) {
        if(categoryRepository.existsByName(categoryDTO.getName())) {
            log.error("(Create) Category already exists: {}", categoryDTO.getName());
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    /**
     * Cập nhật thông tin danh mục.
     *
     * @param id          ID của danh mục cần cập nhật
     * @param categoryDTO đối tượng chứa thông tin mới của danh mục
     * @return đối tượng Category đã được cập nhật
     */
    @Override
    public CategoryResponse updateCategory(Long id, CategoryCreateRequest categoryDTO) {

        if(categoryRepository.existsByName(categoryDTO.getName())) {
            log.error("(Update) Category already exists: {}", categoryDTO.getName());
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setName(categoryDTO.getName());
        log.info("Updating category with id: {}", id);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    /**
     * Xóa danh mục theo ID.
     *
     * @param id ID của danh mục cần xóa
     */
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setIsDeleted(true); // Đánh dấu là đã xóa mềm
        log.info("Deleting category with id: {}", id);
        categoryRepository.save(category); // Lưu lại để cập nhật trạng thái xóa mềm
    }

    /**
     * Khôi phục danh mục đã xóa theo ID.
     *
     * @param id ID của danh mục cần khôi phục
     */
    @Override
    public void restoreCategory(Long id) {
        Category category = categoryRepository.findByIdIgnoreSoftDeleted(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (!category.getIsDeleted()) {
            log.error("Category with id {} is not deleted", id);
            throw new AppException(ErrorCode.CATEGORY_NOT_DELETED);
        }
        category.setIsDeleted(false); // Đánh dấu là chưa xóa
        log.info("Restoring category with id: {}", id);
        categoryRepository.save(category); // Lưu lại để cập nhật trạng thái khôi phục
    }

    /**
     * Lấy tất cả danh mục với phân trang.
     *
     * @param pageable đối tượng Pageable chứa thông tin phân trang
     * @return Page<CategoryResponse> danh sách các danh mục đã được chuyển đổi sang dạng phản hồi
     */
    @Override
    public Page<CategoryResponse> getAllForPageCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::toCategoryResponse);

    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            log.warn("No categories found");
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        log.info("Retrieved {} categories", categories.size());
        return categories.stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        log.info("Retrieved category with id: {}", id);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> getAllDeletedCategories(Pageable pageable) {
        Page<Category> deletedCategories = categoryRepository.findAllDeletedCategories(pageable);
        if (deletedCategories.isEmpty()) {
            log.warn("No deleted categories found");
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        log.info("Retrieved {} deleted categories", deletedCategories.getTotalElements());
        return deletedCategories.map(categoryMapper::toCategoryResponse);
    }
}
