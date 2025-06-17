package com.facenet.mobileshopmanger.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

/*
 * @author TranDat
 * @since 2025-06-16
 *
 * DTO để trả về kết quả của các API.
 * Mã code mặc định là 1000, có thể thay đổi.
 */
public class ApiResponse <T>{
    @Builder.Default
    private int code = 1000;
    private String message;
    private T result;
}
