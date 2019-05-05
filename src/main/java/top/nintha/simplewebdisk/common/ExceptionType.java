package top.nintha.simplewebdisk.common;

public enum ExceptionType {
    SYSTEM_EXCEPTION(1000, "System exception"),
    ITEM_NOT_FOUND(1001, "Item not found"),
    UPLOAD_EXCEPTION(1002, "Failed to upload"),
    NODE_REF_LOOP_EXCEPTION(1003, "Must not make node ref loop"),
    UNLOGIN_EXCEPTION(1004, "Unauthorized access"),
    PASSWORD_MISMATCH_EXCEPTION(1005, "Username or password mismatch"),
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

    public BusinessException toException(String extraMessage){
        return BusinessException.of(this, extraMessage);
    }
}
