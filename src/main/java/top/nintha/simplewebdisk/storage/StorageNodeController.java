package top.nintha.simplewebdisk.storage;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.nintha.simplewebdisk.common.Results;
import top.nintha.simplewebdisk.storage.entity.StorageNode;

import java.util.List;

@Slf4j
@Api(tags = "storage node")
@RestController
@RequestMapping("storage/nodes")
public class StorageNodeController {
    @Autowired
    private StorageNodeService storageNodeService;

    @PostMapping("file")
    public Results<String> upload(@RequestParam MultipartFile file, String parentId) {
        return Results.success(storageNodeService.saveFile(file, parentId));
    }

    @PostMapping("folder")
    public Results<String> saveFolder(@RequestParam String name, String parentId) {
        return Results.success(storageNodeService.saveFolder(name, parentId));
    }

    @GetMapping("byParentId")
    public Results<List<StorageNode>> getByParentId(String parentId) {
        return Results.success(storageNodeService.findByParentId(parentId));
    }

    @DeleteMapping("emptyFolder/{id}")
    public Results<Boolean> deleteEmptyFolder(@PathVariable String id) {
        return Results.success(storageNodeService.deleteEmptyFolder(id));
    }

    @DeleteMapping("file/{id}")
    public Results<Boolean> deleteFile(@PathVariable String id) {
        return Results.success(storageNodeService.deleteFile(id));
    }

    @PutMapping("move")
    public Results<Boolean> moveNode(@RequestParam String id, String targetParentId) {
        if(id.equals(targetParentId)){
            log.warn("[move] can not move into self, id={}", id);
            return Results.success(false);
        }
        return Results.success(storageNodeService.moveNode(id, targetParentId));
    }
}
