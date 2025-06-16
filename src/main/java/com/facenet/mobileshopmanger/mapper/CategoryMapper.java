package com.facenet.mobileshopmanger.mapper;

import com.facenet.mobileshopmanger.dto.response.CategoryResponse;
import com.facenet.mobileshopmanger.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    @Mapping(target = "id", source = "id")
@Mapping(target = "phonesCount", expression = "java(category.getPhones() != null ? category.getPhones().size() : 0)")
    CategoryResponse toCategoryResponse(Category category);
}
