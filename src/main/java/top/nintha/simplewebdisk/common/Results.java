package top.nintha.simplewebdisk.common;

import lombok.Data;

@Data
public class Results<T> {
    private final static int SUCCESS = 0;

    private Integer code;
    private String message;
    private T data;

    public static <T> Results<T> success(T data){
        Results<T> results = new Results<>();
        results.setMessage("success");
        results.setCode(SUCCESS);
        results.setData(data);
        return results;
    }

    public static <T> Results<T> failed(int code, String message){
        Results<T> results = new Results<>();
        results.setMessage(message);
        results.setCode(code);
        results.setData(null);
        return results;
    }

}
