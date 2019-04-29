package top.nintha.simplewebdisk.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Data
public class PageReq {
    private final static int DEFAULT_NUM = 1;
    private final static int DEFAULT_SIZE = 10;

    private int pageNum = DEFAULT_NUM;
    private int pageSize = DEFAULT_SIZE;

    public Pageable toPageable(){
       return PageRequest.of(pageNum - 1, pageSize);
    }

}
