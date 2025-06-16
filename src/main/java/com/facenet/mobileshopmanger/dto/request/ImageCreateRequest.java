package com.facenet.mobileshopmanger.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageCreateRequest {
    @Min(value = 1, message = "Product ID > 0")
    @JsonProperty("phone_id")
    Long phoneId;

    @Size(min = 5, max = 200, message = "Image's name")
    @JsonProperty("image_url")
    String imageUrl;
}
