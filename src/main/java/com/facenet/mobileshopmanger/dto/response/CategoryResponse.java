package com.facenet.mobileshopmanger.dto.response;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;

    private String name;

    private int phonesCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
