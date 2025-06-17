package com.facenet.mobileshopmanger.service;


import com.facenet.mobileshopmanger.dto.request.PhoneCreateRequest;
import com.facenet.mobileshopmanger.dto.response.PhoneResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface PhoneService {
    /**
     * Tạo mới một điện thoại.
     *
     * @param phoneDTO đối tượng chứa thông tin của điện thoại cần tạo
     * @return đối tượng PhoneResponse chứa thông tin của điện thoại vừa tạo
     * @throws IOException nếu có lỗi khi lưu trữ file ảnh
     */
    PhoneResponse createPhone(PhoneCreateRequest phoneDTO) throws IOException;

    /**
     * Lấy tất cả điện thoại với phân trang.
     *
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách các điện thoại
     */
    Page<PhoneResponse> getAllPhones(Pageable pageable);

    /**
     * Lấy tất ra điện thoại theo ID.
     * @param id ID của điện thoại cần lấy
     * @return đối tượng PhoneResponse chứa thông tin của điện thoại
     */
    PhoneResponse getPhoneById(Long id);

    /**
     * Cập nhật thông tin điện thoại.
     *
     * @param id       ID của điện thoại cần cập nhật
     * @param phoneDTO đối tượng chứa thông tin mới của điện thoại
     * @return đối tượng PhoneResponse đã được cập nhật
     * @throws IOException nếu có lỗi khi lưu trữ file ảnh
     */
    PhoneResponse updatePhone(Long id, PhoneCreateRequest phoneDTO) throws IOException;

    /**
     * Xóa mềm điện thoại theo ID.
     *
     * @param id ID của điện thoại cần xóa
     */
    void deletePhone(Long id);

    /**
     * Khôi phục điện thoại đã xóa theo ID.
     *
     * @param id ID của điện thoại cần khôi phục
     */
    void restorePhone(Long id);

    /**
     * Lấy tất cả điện thoại đã xóa mềm với phân trang.
     *
     * @param pageable thông tin phân trang
     * @return Page chứa danh sách các điện thoại đã xóa mềm
     */
    Page<PhoneResponse> getAllDeletedPhones(Pageable pageable);
}