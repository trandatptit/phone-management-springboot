package com.facenet.mobileshopmanger.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "invalid message key", HttpStatus.BAD_REQUEST),
    CATEGORY_INVALID_NAME(1002, "Category name must be at least 2 character and at most 50 characters", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(1003, "Category existed", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_EXISTED(1004, "Category not existed", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1005, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_DELETED(1006, "Category is not deleted", HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(1007, "File not found", HttpStatus.NOT_FOUND),
    PHONE_NAME_AND_MODEL_EXISTED(1008, "Phone name and model already existed", HttpStatus.BAD_REQUEST),
    PHONE_IMAGE_LIMIT_EXCEEDED(1009, "Phone image must be at most 5 images", HttpStatus.BAD_REQUEST),
    PHONE_NOT_FOUND(1010, "Phone not found", HttpStatus.NOT_FOUND),
    PHONE_NOT_DELETED(1011, "Phone is not deleted", HttpStatus.BAD_REQUEST),
    ;

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
