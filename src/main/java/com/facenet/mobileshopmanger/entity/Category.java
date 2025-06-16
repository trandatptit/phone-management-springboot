package com.facenet.mobileshopmanger.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "is_deleted = false") // Chỉ lấy các bản ghi chưa bị xóa mềm
public class Category {
    @Id // Khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tăng tự động
    private Long id; // Mã danh mục

    private String name; // Tên danh mục (ví dụ: Samsung, Apple, Xiaomi, ...)

    @Column(name = "created_at", updatable = false) // Ngày tạo (không cho update)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted") // Trạng thái xóa mềm, mặc định là false
    @Builder.Default
    private Boolean isDeleted = false; // Trạng thái xóa mềm (false: chưa xóa, true: đã xóa)

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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Quan hệ một - nhiều với bảng điện thoại
    @JsonManagedReference
    private List<Phone> phones; // Danh sách điện thoại thuộc danh mục
}
