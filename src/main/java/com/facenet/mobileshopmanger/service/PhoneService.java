package com.facenet.mobileshopmanger.service;


import com.facenet.mobileshopmanger.dto.request.PhoneCreateRequest;
import com.facenet.mobileshopmanger.dto.response.PhoneResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface PhoneService {
    PhoneResponse createPhone(PhoneCreateRequest phoneDTO) throws IOException;

    Page<PhoneResponse> getAllPhones(Pageable pageable);

    PhoneResponse getPhoneById(Long id);

    PhoneResponse updatePhone(Long id, PhoneCreateRequest phoneDTO) throws IOException;

    void deletePhone(Long id);

    void restorePhone(Long id);
    Page<PhoneResponse> getAllDeletedPhones(Pageable pageable);
}