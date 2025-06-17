package com.facenet.mobileshopmanger.mapper;

import com.facenet.mobileshopmanger.dto.request.PhoneCreateRequest;
import com.facenet.mobileshopmanger.dto.response.PhoneResponse;
import com.facenet.mobileshopmanger.entity.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
/* * @author TranDat
 * @since 2025-06-16
 *
 * Mapper để chuyển đổi giữa Phone entity và Phone DTO.
 * Sử dụng MapStruct để tự động tạo mã chuyển đổi.
 */
public interface PhoneMapper {


    /**
     * Converts a PhoneDTO to a Phone entity.
     *
     * @param phoneDTO the PhoneDTO to convert
     * @return the converted Phone entity
     */
    Phone toPhone(PhoneCreateRequest phoneDTO);

    /**
     * Converts a Phone entity to a PhoneResponseDTO.
     *
     * @param phone the Phone entity to convert
     * @return the converted PhoneResponseDTO
     */

    @Mapping(source = "imageAvtUrl", target = "avatarUrl")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(target = "imageUrls",
            expression = "java(phone.getImages() != null ? phone.getImages()." +
                    "stream().map(com.facenet.mobileshopmanger.entity.Image::getUrl).toList() : null)")
    PhoneResponse toPhoneResponse(Phone phone);

    /**
     * Converts a list of Phone entities to a list of PhoneResponseDTOs.
     *
     * @param phones the list of Phone entities to convert
     * @return the list of converted PhoneResponseDTOs
     */
    List<PhoneResponse> toPhoneResponseDTOList(List<Phone> phones);

}
