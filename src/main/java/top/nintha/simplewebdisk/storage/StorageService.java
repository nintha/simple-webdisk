package top.nintha.simplewebdisk.storage;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.nintha.simplewebdisk.common.ExceptionType;

@Slf4j
@Service
public class StorageService {
    @Autowired
    private GridFsTemplate gridFsTemplate;


    public String storeFile(MultipartFile file) {
        try {
            ObjectId objectId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
            String token = objectId.toHexString();
            log.info("[upload] filename={}, token={}", file.getOriginalFilename(), token);
            return token;
        } catch (Exception e) {
            log.error("[upload] error", e);
            throw ExceptionType.UPLOAD_EXCEPTION.toException();
        }
    }

    public GridFsResource getResourceByToken(String token) {
        return gridFsTemplate.getResource(getGridFsFileByToken(token));
    }

    private GridFSFile getGridFsFileByToken(String token) {
        return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(token)));
    }

    public boolean deleteByToken(String token) {
        GridFSFile fsFile = getGridFsFileByToken(token);
        if(fsFile == null){
            return false;
        }else{
            gridFsTemplate.delete(new Query(Criteria.where("_id").is(token)));
            return true;
        }
    }

}
