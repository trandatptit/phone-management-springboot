package com.facenet.mobileshopmanger.exception;


/* * @author TranDat
 * @since 2025-06-16
 *
 * Lớp đại diện cho một ngoại lệ trong ứng dụng.
 * Kế thừa từ RuntimeException để có thể ném ra mà không cần phải khai báo trong phương thức.
 */
public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
