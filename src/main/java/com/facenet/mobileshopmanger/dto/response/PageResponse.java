package com.facenet.mobileshopmanger.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

/**
 * @author TranDat
 * @since 2025-06-16
 *
 * DTO để trả về kết quả phân trang.
 * Chứa danh sách các phần tử trong trang, số trang hiện tại, kích thước của mỗi trang,
 * tổng số phần tử và tổng số trang.
 */
public class PageResponse <T>{
    private List<T> content; // danh sách các phần tử trong trang
    private int page; // số trang hiện tại (bắt đầu từ 0)
    private int size; // kích thước của mỗi trang
    private long totalElements; // tổng số phần tử trong tất cả các trang
    private int totalPages; // tổng số trang
    private boolean last; // có phải là trang cuối cùng hay không
}
