package top.nintha.simplewebdisk.storage;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.nintha.simplewebdisk.common.ExceptionType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Api(tags = "storage file")
@Slf4j
@RestController
@RequestMapping("storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @GetMapping("download/{token}")
    public void download(HttpServletResponse response, @PathVariable String token) throws IOException {
        GridFsResource resource = storageService.getResourceByToken(token);
        if(resource == null){
            throw ExceptionType.ITEM_NOT_FOUND.toException();
        }

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        response.setHeader(HttpHeaders.CONTENT_TYPE, resource.getContentType());
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));
        try (InputStream in = resource.getInputStream(); OutputStream out = response.getOutputStream();) {
            IOUtils.copy(in, out);
        }
    }
}
