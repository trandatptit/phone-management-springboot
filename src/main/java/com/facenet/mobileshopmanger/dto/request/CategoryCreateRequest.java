package com.facenet.mobileshopmanger.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateRequest {

    /**
     * Tên danh mục (ví dụ: Samsung, Apple, Xiaomi, ...).
     * Tên phải có độ dài từ 2 đến 50 ký tự.
     */
    @Size(min = 2, max = 50, message = "CATEGORY_INVALID_NAME")
    private String name;
}
