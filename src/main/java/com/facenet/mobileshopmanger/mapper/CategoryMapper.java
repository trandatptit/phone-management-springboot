package com.facenet.mobileshopmanger.mapper;

import com.facenet.mobileshopmanger.dto.response.CategoryResponse;
import com.facenet.mobileshopmanger.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
/* * @author TranDat
 * @since 2025-06-16
 *
 * Mapper để chuyển đổi giữa Category entity và Category DTO.
 * Sử dụng MapStruct để tự động tạo mã chuyển đổi.
 */
public interface CategoryMapper {

//    @Mapping(target = "id", source = "id")
@Mapping(target = "phonesCount", expression = "java(category.getPhones() != null ? category.getPhones().size() : 0)")
    CategoryResponse toCategoryResponse(Category category);
}
