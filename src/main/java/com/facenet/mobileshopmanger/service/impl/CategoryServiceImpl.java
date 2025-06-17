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

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest categoryDTO) {
        // kiêm tra xem danh mục đã tồn tại hay chưa
        if(categoryRepository.existsByName(categoryDTO.getName())) {
            log.error("(Create) Category already exists: {}", categoryDTO.getName());
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }


    @Override
    public CategoryResponse updateCategory(Long id, CategoryCreateRequest categoryDTO) {

        // Kiểm tra xem danh mục đã tồn tại hay chưa
        if(categoryRepository.existsByName(categoryDTO.getName())) {
            log.error("(Update) Category already exists: {}", categoryDTO.getName());
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        // Lấy danh mục theo ID, nếu không tìm thấy thì ném ngoại lệ
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setName(categoryDTO.getName());
        log.info("Updating category with id: {}", id);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setIsDeleted(true); // Đánh dấu là đã xóa mềm
        log.info("Deleting category with id: {}", id);
        categoryRepository.save(category); // Lưu lại để cập nhật trạng thái xóa mềm
    }

    @Override
    public void restoreCategory(Long id) {
        // Tìm kiếm danh mục theo ID, bỏ qua các bản ghi đã xóa mềm
        Category category = categoryRepository.findByIdIgnoreSoftDeleted(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        // Kiểm tra xem danh mục đã bị xóa mềm hay chưa
        if (!category.getIsDeleted()) {
            log.error("Category with id {} is not deleted", id);
            throw new AppException(ErrorCode.CATEGORY_NOT_DELETED);
        }
        category.setIsDeleted(false); // Đánh dấu là chưa xóa
        log.info("Restoring category with id: {}", id);
        categoryRepository.save(category); // Lưu lại để cập nhật trạng thái khôi phục
    }

    @Override
    public Page<CategoryResponse> getAllForPageCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::toCategoryResponse);

    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        // Kiểm tra xem danh sách danh mục có rỗng hay không
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
        // Tìm kiếm tất cả danh mục đã xóa mềm với phân trang
        Page<Category> deletedCategories = categoryRepository.findAllDeletedCategories(pageable);
        // Kiểm tra xem có danh mục đã xóa mềm nào không
        if (deletedCategories.isEmpty()) {
            log.warn("No deleted categories found");
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        log.info("Retrieved {} deleted categories", deletedCategories.getTotalElements());
        return deletedCategories.map(categoryMapper::toCategoryResponse);
    }
}
