package com.facenet.mobileshopmanger.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryCreateRequest {

    @Size(min = 2, max = 50, message = "CATEGORY_INVALID_NAME")
    private String name;
}
