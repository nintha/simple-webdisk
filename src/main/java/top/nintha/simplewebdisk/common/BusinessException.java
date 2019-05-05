package top.nintha.simplewebdisk.common;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private int code;
    private String message;

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BusinessException of(ExceptionType type) {
        return new BusinessException(type.getCode(), type.getMessage());
    }

    public static BusinessException of(ExceptionType type, String extraMessage) {
        return new BusinessException(type.getCode(), type.getMessage() + ", " + extraMessage);
    }
}
