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
/**
 * @author TranDat
 * @since 2025-06-16
 */
public class ImageCreateRequest {

    /**
     * ID của điện thoại mà hình ảnh thuộc về.
     * ID phải lớn hơn 0.
     */
    @Min(value = 1, message = "Product ID > 0")
    @JsonProperty("phone_id")
    Long phoneId;

    /**
     * URL của hình ảnh.
     * Tên phải có độ dài từ 5 đến 200 ký tự.
     */
    @Size(min = 5, max = 200, message = "Image's name")
    @JsonProperty("image_url")
    String imageUrl;
}
