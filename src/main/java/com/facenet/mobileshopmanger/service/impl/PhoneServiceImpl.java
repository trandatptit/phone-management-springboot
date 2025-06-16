package com.facenet.mobileshopmanger.service.impl;

import com.facenet.mobileshopmanger.dto.request.PhoneCreateRequest;
import com.facenet.mobileshopmanger.dto.response.PhoneResponse;
import com.facenet.mobileshopmanger.entity.Category;
import com.facenet.mobileshopmanger.entity.Image;
import com.facenet.mobileshopmanger.entity.Phone;
import com.facenet.mobileshopmanger.exception.AppException;
import com.facenet.mobileshopmanger.exception.ErrorCode;
import com.facenet.mobileshopmanger.mapper.PhoneMapper;
import com.facenet.mobileshopmanger.repository.CategoryRepository;
import com.facenet.mobileshopmanger.repository.ImageRepository;
import com.facenet.mobileshopmanger.repository.PhoneRepository;
import com.facenet.mobileshopmanger.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PhoneServiceImpl implements PhoneService {

    CategoryRepository categoryRepository;
    PhoneRepository phoneRepository;
    ImageRepository imageRepository;
    PhoneMapper phoneMapper;
    Logger log = LogManager.getLogger();

    /**
     * Tạo mới một điện thoại.
     *
     * @param phoneCreateRequest đối tượng chứa thông tin của điện thoại cần tạo
     * @return đối tượng PhoneResponse chứa thông tin của điện thoại vừa tạo
     * @throws IOException nếu có lỗi khi lưu trữ file ảnh
     */
    @Override
    public PhoneResponse createPhone(PhoneCreateRequest phoneCreateRequest) throws IOException {
        Category category = categoryRepository.findById(phoneCreateRequest.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", phoneCreateRequest.getCategoryId());
                    return new AppException(ErrorCode.CATEGORY_NOT_FOUND);
                });
        if(phoneRepository.existsByNameAndModel(phoneCreateRequest.getName(), phoneCreateRequest.getModel())) {
            log.error("Phone with name '{}' and model '{}' already exists", phoneCreateRequest.getName(), phoneCreateRequest.getModel());
            throw new AppException(ErrorCode.PHONE_NAME_AND_MODEL_EXISTED);
        }
        if(phoneCreateRequest.getFiles() != null && phoneCreateRequest.getFiles().size() > 5) {
            log.error("Phone image limit exceeded: {}", phoneCreateRequest.getFiles().size());
            throw new AppException(ErrorCode.PHONE_IMAGE_LIMIT_EXCEEDED);
        }
        Phone phone = Phone.builder()
                .name(phoneCreateRequest.getName())
                .category(category)
                .model(phoneCreateRequest.getModel())
                .os(phoneCreateRequest.getOs())
                .color(phoneCreateRequest.getColor())
                .ram(phoneCreateRequest.getRam())
                .rom(phoneCreateRequest.getRom())
                .screen(phoneCreateRequest.getScreen())
                .camera(phoneCreateRequest.getCamera())
                .status(phoneCreateRequest.getStatus())
                .priceImport(phoneCreateRequest.getPriceImport())
                .priceSale(phoneCreateRequest.getPriceSale())
                .quantityInStock(phoneCreateRequest.getQuantityInStock())
                .description(phoneCreateRequest.getDescription())
                .build();
        phoneRepository.save(phone);
        phone.setImageAvtUrl(storeFile(phoneCreateRequest.getAvatar()));
        // Lưu danh sách ảnh (nếu có)
        if (phoneCreateRequest.getFiles() != null) {
            int count = 0;
            for (MultipartFile file : phoneCreateRequest.getFiles()) {
                if (file != null && !file.isEmpty()) {
                    if (count >= 5) break;
                    String imageUrl = storeFile(file);
                    Image image = Image.builder()
                            .phone(phone)
                            .url(imageUrl)
                            .build();
                    imageRepository.save(image);
                    count++;
                }
            }
        }

        // Load lại image để set vào phone nếu cần (tuỳ mapping)
        List<Image> images = imageRepository.findByPhoneId(phone.getId());
        phone.setImages(images);

        return phoneMapper.toPhoneResponse(phoneRepository.save(phone));
    }



    /**
     * Lấy danh sách tất cả điện thoại với phân trang.
     *
     * @param pageable đối tượng Pageable chứa thông tin phân trang
     * @return Page chứa danh sách PhoneResponseDTO
     */
    @Override
    public Page<PhoneResponse> getAllPhones(Pageable pageable) {
        Page<Phone> phones = phoneRepository.findAll(pageable);
        return phones.map(phoneMapper::toPhoneResponse);
    }


    /**
     * Lấy thông tin điện thoại theo ID.
     *
     * @param id ID của điện thoại cần lấy thông tin
     * @return đối tượng PhoneResponseDTO chứa thông tin của điện thoại
     * @throws AppException nếu không tìm thấy điện thoại với ID đã cho
     */
    @Override
    public PhoneResponse getPhoneById(Long id) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Phone not found with id: {}", id);
                    return new AppException(ErrorCode.PHONE_NOT_FOUND);
                });
        return phoneMapper.toPhoneResponse(phone);
    }


    /**
     * Cập nhật thông tin điện thoại theo ID.
     *
     * @param id        ID của điện thoại cần cập nhật
     * @param phoneDTO đối tượng chứa thông tin cập nhật
     * @return đối tượng PhoneResponseDTO chứa thông tin của điện thoại sau khi cập nhật
     * @throws AppException nếu có lỗi
     */
    @Override
    public PhoneResponse updatePhone(Long id, PhoneCreateRequest phoneDTO) throws IOException {
        Phone existingPhone = phoneRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Phone not found with id: {}", id);
                    return new AppException(ErrorCode.PHONE_NOT_FOUND);
                });

        if(phoneRepository.existsByNameAndModelAndIdNot(phoneDTO.getName(), phoneDTO.getModel(), id)) {
            log.error("Phone with name '{}' and model '{}' already exists", phoneDTO.getName(), phoneDTO.getModel());
            throw new AppException(ErrorCode.PHONE_NAME_AND_MODEL_EXISTED);
        }

        Category category = categoryRepository.findById(phoneDTO.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", phoneDTO.getCategoryId());
                    return new AppException(ErrorCode.CATEGORY_NOT_FOUND);
                });

        existingPhone.setName(phoneDTO.getName());
        existingPhone.setCategory(category);
        existingPhone.setModel(phoneDTO.getModel());
        existingPhone.setOs(phoneDTO.getOs());
        existingPhone.setColor(phoneDTO.getColor());
        existingPhone.setRam(phoneDTO.getRam());
        existingPhone.setRom(phoneDTO.getRom());
        existingPhone.setScreen(phoneDTO.getScreen());
        existingPhone.setCamera(phoneDTO.getCamera());
        existingPhone.setStatus(phoneDTO.getStatus());
        existingPhone.setPriceImport(phoneDTO.getPriceImport());
        existingPhone.setPriceSale(phoneDTO.getPriceSale());
        existingPhone.setQuantityInStock(phoneDTO.getQuantityInStock());
        existingPhone.setDescription(phoneDTO.getDescription());

        if (phoneDTO.getAvatar() != null) {
            String avatarUrl = storeFile(phoneDTO.getAvatar());
            existingPhone.setImageAvtUrl(avatarUrl);
        }

        // Cập nhật ảnh nếu có
        if (phoneDTO.getFiles() != null) {
            // Xóa tất cả ảnh cũ liên kết với điện thoại này
            List<Image> existingImages = imageRepository.findByPhoneId(existingPhone.getId());
            for (Image image : existingImages) {
                imageRepository.delete(image);
                deletePhoneImage(image.getUrl());
            }
            int count = 0;
            for (MultipartFile file : phoneDTO.getFiles()) {
                if (file != null && !file.isEmpty()) {
                    if (count >= 5) break;
                    String imageUrl = storeFile(file);
                    Image image = Image.builder()
                            .phone(existingPhone)
                            .url(imageUrl)
                            .build();
                    imageRepository.save(image);
                    count++;
                }
            }
        }
        return phoneMapper.toPhoneResponse(phoneRepository.save(existingPhone));
    }

    /**
     * Xóa điện thoại theo ID (xóa mềm).
     *
     * @param id ID của điện thoại cần xóa
     * @throws AppException nếu không tìm thấy điện thoại với ID đã cho
     */
    @Override
    public void deletePhone(Long id) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Phone not found with id: {}", id);
                    return new AppException(ErrorCode.PHONE_NOT_FOUND);
                });
        phone.setIsDeleted(true);
        phoneRepository.save(phone);
    }


    /**
     * Lưu trữ file ảnh vào thư mục uploads.
     *
     * @param file đối tượng MultipartFile chứa file ảnh cần lưu
     * @return tên file mới được lưu trữ
     * @throws IOException nếu có lỗi khi lưu trữ file
     */
    public String storeFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path filePath = Paths.get(uploadDir.toString(), newFileName);

        // Sao chep file vào thư mục đích
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }

    public void deletePhoneImage(String fileName) throws IOException {
        Path updateDir = Paths.get("uploads");
        Path filePath = Paths.get(updateDir.toString(), fileName);
        if(Files.exists(filePath)){
            Files.delete(filePath);
        }
        else{
            log.error("File not found: {}", fileName);
            throw new AppException(ErrorCode.FILE_NOT_FOUND);
        }
    }

    @Override
    public void restorePhone(Long id) {
        Phone phone = phoneRepository.findByIdIgnoreSoftDeleted(id)
                .orElseThrow(() -> {
                    log.error("Phone not found with id: {}", id);
                    return new AppException(ErrorCode.PHONE_NOT_FOUND);
                });
        if (!phone.getIsDeleted()) {
            log.error("Phone with id {} is not deleted", id);
            throw new AppException(ErrorCode.PHONE_NOT_DELETED);
        }
        phone.setIsDeleted(false);
        phoneRepository.save(phone);
    }

    @Override
    public Page<PhoneResponse> getAllDeletedPhones(Pageable pageable) {
        Page<Phone> deletedPhones = phoneRepository.findAllDeletedPhones(pageable);
        return deletedPhones.map(phoneMapper::toPhoneResponse);
    }
}
