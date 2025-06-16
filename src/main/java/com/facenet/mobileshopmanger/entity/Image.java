package com.facenet.mobileshopmanger.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {
    @Id // Khóa chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tăng tự động
    private Long id; // Mã ảnh

    private String url; // Đường dẫn ảnh

    @ManyToOne(fetch = FetchType.LAZY) // Quan hệ nhiều - một với bảng điện thoại
    @JoinColumn(name = "phone_id") // Khóa ngoại tới bảng điện thoại
    private Phone phone; // Điện thoại liên kết với ảnh

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
