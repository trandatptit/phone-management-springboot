package com.facenet.mobileshopmanger.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class PhoneResponse {
    private Long id;
    private String name;
    private String categoryName;
    private String model;
    private String os;
    private String color;
    private int ram;
    private int rom;
    private String screen;
    private String camera;
    private BigDecimal priceImport;
    private BigDecimal priceSale;
    private int quantityInStock;
    private String avatarUrl;
    private List<String> imageUrls;
    private String description;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
