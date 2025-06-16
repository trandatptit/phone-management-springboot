package com.facenet.mobileshopmanger.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneCreateRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @NotNull(message = "Mã danh mục không được để trống")
    private Long categoryId; // Gửi ID thôi, không gửi đối tượng Category

    private String model;

    private String os;

    private String color;

    @Min(value = 1, message = "RAM phải lớn hơn 0")
    private int ram;

    @Min(value = 1, message = "ROM phải lớn hơn 0")
    private int rom;

    private String screen;

    private String camera;

    @NotNull(message = "Giá nhập không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá nhập phải lớn hơn 0")
    private BigDecimal priceImport;

    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá bán phải lớn hơn 0")
    private BigDecimal priceSale;

    @Min(value = 0, message = "Tồn kho không hợp lệ")
    private int quantityInStock;

    private String description;

    private Boolean status;

    // Ảnh đại diện (avatar) gửi lên
    private MultipartFile avatar;

    // Danh sách ảnh phụ gửi lên
    private List<MultipartFile> files;
}