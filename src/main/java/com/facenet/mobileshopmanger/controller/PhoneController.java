package com.facenet.mobileshopmanger.controller;


import com.facenet.mobileshopmanger.dto.request.PhoneCreateRequest;
import com.facenet.mobileshopmanger.dto.response.ApiResponse;
import com.facenet.mobileshopmanger.dto.response.PageResponse;
import com.facenet.mobileshopmanger.dto.response.PhoneResponse;
import com.facenet.mobileshopmanger.service.PhoneService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.util.List;
@RestController
@RequestMapping("${api.prefix}/phones")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin(origins = "https://phone-management-react.vercel.app/")
public class PhoneController {

    PhoneService phoneService;

    Logger log  = LogManager.getLogger();


    /**
     * Tạo mới một điện thoại.
     *
     * @param phoneDTO đối tượng chứa thông tin của điện thoại cần tạo
     * @param result   kết quả kiểm tra hợp lệ của dữ liệu
     * @return ApiResponse chứa thông tin về kết quả tạo mới
     * @throws IOException nếu có lỗi khi lưu trữ file ảnh
     */
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<PhoneResponse> createPhone(
            @Valid @ModelAttribute PhoneCreateRequest phoneDTO,
            BindingResult result
    ) throws IOException {
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            log.error("Validation errors: {}", errorMessages);
            return ApiResponse.<PhoneResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors")
                    .result(null)
                    .build();
        }
        PhoneResponse newPhone = phoneService.createPhone(phoneDTO);

        return ApiResponse.<PhoneResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Phone created successfully")
                .result(newPhone)
                .build();
    }

    /**
     * Lấy danh sách tất cả điện thoại với phân trang.
     *
     * @param pageable đối tượng chứa thông tin phân trang
     * @return ApiResponse chứa danh sách điện thoại và thông tin phân trang
     */
    @GetMapping("all")
    public ApiResponse<PageResponse<PhoneResponse>> getAllPhones(Pageable pageable) {
        Page<PhoneResponse> phones = phoneService.getAllPhones(pageable);
        PageResponse<PhoneResponse> pageResponse = PageResponse.<PhoneResponse>builder()
                .content(phones.getContent())
                .page(phones.getNumber())
                .size(phones.getSize())
                .totalElements(phones.getTotalElements())
                .totalPages(phones.getTotalPages())
                .last(phones.isLast())
                .build();
        return ApiResponse.<PageResponse<PhoneResponse>>builder()
                .result(pageResponse)
                .build();
    }

    /**
     * Lấy thông tin điện thoại theo ID.
     *
     * @param id ID của điện thoại cần lấy thông tin
     * @return ApiResponse chứa thông tin của điện thoại
     */
    @GetMapping("/{id}")
    public ApiResponse<PhoneResponse> getPhoneById(@PathVariable Long id) {
        PhoneResponse phone = phoneService.getPhoneById(id);
        if (phone == null) {
            return ApiResponse.<PhoneResponse>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Phone not found")
                    .result(null)
                    .build();
        }
        return ApiResponse.<PhoneResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Phone retrieved successfully")
                .result(phone)
                .build();
    }

    /**
     * Cập nhật thông tin điện thoại.
     *
     * @param id       ID của điện thoại cần cập nhật
     * @param phoneDTO đối tượng chứa thông tin mới của điện thoại
     * @param result   kết quả kiểm tra hợp lệ của dữ liệu
     * @return ApiResponse chứa thông tin về kết quả cập nhật
     * @throws IOException nếu có lỗi khi lưu trữ file ảnh
     */
    @PutMapping("/update")
    public ApiResponse<PhoneResponse> updatePhone(
            @RequestParam Long id,
            @Valid @ModelAttribute PhoneCreateRequest phoneDTO,
            BindingResult result
    ) throws IOException {
        if(result.hasErrors()){
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            log.error("Validation errors: {}", errorMessages);
            return ApiResponse.<PhoneResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Validation errors")
                    .result(null)
                    .build();
        }
        PhoneResponse updatedPhone = phoneService.updatePhone(id, phoneDTO);
        if (updatedPhone == null) {
            return ApiResponse.<PhoneResponse>builder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Phone not found")
                    .result(null)
                    .build();
        }
        return ApiResponse.<PhoneResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Phone updated successfully")
                .result(updatedPhone)
                .build();
    }

    /**
     * Xóa một điện thoại theo ID (xóa mềm).
     *
     * @param id ID của điện thoại cần xóa
     * @return ApiResponse chứa thông tin về kết quả xóa
     */
    @DeleteMapping("delete/{id}")
    public ApiResponse<String> deletePhone(@PathVariable Long id) {
       phoneService.deletePhone(id);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Phone deleted successfully")
                .build();
    }

    /**
     * Khôi phục một điện thoại đã bị xóa theo ID.
     *
     * @param id ID của điện thoại cần khôi phục
     * @return ApiResponse chứa thông tin về kết quả khôi phục
     */
    @PutMapping("restore/{id}")
    public ApiResponse<String> restorePhone(@PathVariable Long id) {
        phoneService.restorePhone(id);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .result("Phone restored successfully")
                .build();
    }

    /**
     * Lấy danh sách tất cả điện thoại đã bị xóa (xóa mềm) với phân trang.
     *
     * @param pageable đối tượng chứa thông tin phân trang
     * @return ApiResponse chứa danh sách điện thoại đã bị xóa và thông tin phân trang
     */
    @GetMapping("/deleted")
    public ApiResponse<PageResponse<PhoneResponse>> getAllDeletedPhones(Pageable pageable) {
        Page<PhoneResponse> deletedPhones = phoneService.getAllDeletedPhones(pageable);
        PageResponse<PhoneResponse> pageResponse = PageResponse.<PhoneResponse>builder()
                .content(deletedPhones.getContent())
                .page(deletedPhones.getNumber())
                .size(deletedPhones.getSize())
                .totalElements(deletedPhones.getTotalElements())
                .totalPages(deletedPhones.getTotalPages())
                .last(deletedPhones.isLast())
                .build();
        return ApiResponse.<PageResponse<PhoneResponse>>builder()
                .result(pageResponse)
                .build();
    }
}
