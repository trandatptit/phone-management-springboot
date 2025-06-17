package com.facenet.mobileshopmanger.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "phones")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "is_deleted = false") // Chỉ lấy các bản ghi chưa bị xóa

/**
 * @author TranDat
 * @since 2025-06-16
 *
 * Thực thể đại diện cho bảng điện thoại trong cơ sở dữ liệu.
 * Mỗi điện thoại có thể thuộc về một thương hiệu và có nhiều ảnh liên quan.
 */
public class Phone {
    @Id // Khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tăng tự động
    private Long id; // Mã điện thoại

    private String name; // Tên sản phẩm (ví dụ: iPhone 15 Pro Max)

    @ManyToOne(fetch = FetchType.LAZY) // Quan hệ nhiều - một với bảng thương hiệu
    @JoinColumn(name = "category_id") // Khóa ngoại tới bảng thương hiệu
    @JsonBackReference
    private Category category; // Thương hiệu (Apple, Samsung...)

    @OneToMany(mappedBy = "phone", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Quan hệ một - nhiều với bảng ảnh
    private List<Image> images; // Danh sách ảnh liên quan đến điện thoại

    private String model; // Mã sản phẩm hoặc phiên bản

    private String os; // Hệ điều hành (iOS, Android)

    private String color; // Màu sắc

    private int ram; // RAM (GB)

    private int rom; // Bộ nhớ trong (GB)

    private String screen; // Kích thước & thông số màn hình (ví dụ: 6.7", 120Hz)

    private String camera; // Mô tả camera (ví dụ: 48MP + 12MP)

    @Column(name = "price_import", precision = 15, scale = 2) // Giá nhập (giá nội bộ)
    private BigDecimal priceImport;

    @Column(name = "price_sale", precision = 15, scale = 2) // Giá bán
    private BigDecimal priceSale;

    @Column(name = "quantity_in_stock") // Số lượng tồn kho
    private int quantityInStock;

    @Column(name = "image_url") // Đường dẫn ảnh đại diện sản phẩm
    private String imageAvtUrl;

    @Column(columnDefinition = "TEXT") // Mô tả chi tiết, có thể dài
    private String description;

    private Boolean status = true; // Trạng thái còn bán (true) hay đã ngừng bán (false)

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    @Builder.Default// Trạng thái đã xóa (xóa mềm)
    private Boolean isDeleted = false; // Trạng thái đã xóa (true) hay chưa (false)

    @Column(name = "created_at", updatable = false) // Ngày tạo (không cho update)
    private LocalDateTime createdAt;

    @Column(name = "updated_at") // Ngày cập nhật gần nhất
    private LocalDateTime updatedAt;

    // Khi tạo mới, gán ngày tạo và cập nhật
    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }

    // Khi cập nhật, gán lại ngày cập nhật
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
