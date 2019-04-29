package top.nintha.simplewebdisk.common;

public enum ExceptionType {
    SYSTEM_EXCEPTION(1000, "system exception"),
    ITEM_NOT_FOUND(1001, "item not found"),
    UPLOAD_EXCEPTION(1002, "failed to upload"),
    NODE_REF_LOOP_EXCEPTION(1002, "must not make node ref loop"),
    ;

    private int code;
    private String message;

    ExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public BusinessException toException(){
        return BusinessException.of(this);
    }
}
