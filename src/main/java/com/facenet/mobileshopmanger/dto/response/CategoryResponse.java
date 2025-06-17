package com.facenet.mobileshopmanger.dto.response;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

/**
 * @author TranDat
 * @since 2025-06-16
 *
 * DTO để trả về thông tin danh mục.
 */
public class CategoryResponse {
    private Long id;

    private String name;

    private int phonesCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
