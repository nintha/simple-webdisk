package top.nintha.simplewebdisk.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.nintha.simplewebdisk.common.ExceptionType;
import top.nintha.simplewebdisk.storage.dao.StorageNodeDao;
import top.nintha.simplewebdisk.storage.entity.StorageNode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StorageNodeService {
    @Autowired
    private StorageNodeDao storageNodeDao;
    @Autowired
    private StorageService storageService;


    public String saveFolder(String name, String parentId) {
        StorageNode node = StorageNode.builder()
                .name(name)
                .fileFlag(false)
                .parentId(parentId)
                .createTime(LocalDateTime.now())
                .modifyTime(LocalDateTime.now())
                .build();

        StorageNode insert = storageNodeDao.insert(node);
        return insert.getId();
    }

    public String saveFile(MultipartFile file, String parentId) {
        String token = storageService.storeFile(file);

        StorageNode node = StorageNode.builder()
                .name(file.getOriginalFilename())
                .parentId(parentId)
                .fileFlag(true)
                .token(token)
                .createTime(LocalDateTime.now())
                .modifyTime(LocalDateTime.now())
                .build();

        StorageNode saved = storageNodeDao.save(node);
        return saved.getId();
    }

    public List<StorageNode> findByParentId(String parentId) {
        return storageNodeDao.findByParentId(parentId);
    }

    public boolean deleteFile(String id) {
        Optional<StorageNode> optional = storageNodeDao.findById(id);
        return optional.filter(StorageNode::getFileFlag).map(node -> {
            storageNodeDao.deleteById(id);
            storageService.deleteByToken(node.getToken());
            return true;
        }).orElse(false);
    }

    public boolean deleteEmptyFolder(String id) {
        Optional<StorageNode> optional = storageNodeDao.findById(id);
        return optional.filter(StorageNode::getFolderFlag)
                .filter(node -> storageNodeDao.countByParentId(id) == 0)
                .map(node -> {
                    storageNodeDao.deleteById(id);
                    storageService.deleteByToken(node.getToken());
                    return true;
                }).orElse(false);
    }

    public boolean moveNode(String childId, String targetParentId) {
        if (checkRelation(targetParentId, childId)) {
            log.error("ancestor node can not be child node, childId={}, targetParentId={}", childId, targetParentId);
            throw ExceptionType.NODE_REF_LOOP_EXCEPTION.toException();
        }

        Optional<StorageNode> optional = storageNodeDao.findById(childId);
        return optional.map(node -> {
            if (targetParentId == null) {
                node.setParentId(null);
                node.setModifyTime(LocalDateTime.now());
                storageNodeDao.save(node);
                return true;
            } else {
                return storageNodeDao.findById(targetParentId).map(parent -> {
                    node.setParentId(targetParentId);
                    node.setModifyTime(LocalDateTime.now());
                    storageNodeDao.save(node);
                    return true;
                }).orElse(false);
            }
        }).orElse(false);
    }

    public boolean checkRelation(String childId, String ancestorId) {
        if (ancestorId == null && childId != null) {
            return true;
        }
        List<StorageNode> allParentNodes = getAllParentNodes(childId);
        return allParentNodes.stream().map(StorageNode::getId).anyMatch(it -> it.equals(ancestorId));
    }

    public List<StorageNode> getAllParentNodes(String id) {
        HashSet<String> idSet = Sets.newHashSet();

        ArrayList<StorageNode> list = Lists.newArrayList();
        Optional<StorageNode> optional = storageNodeDao.findById(id);
        String parentId = optional.map(StorageNode::getParentId).orElse(null);
        while (parentId != null) {
            Optional<StorageNode> parentNode = storageNodeDao.findById(parentId);
            parentNode.ifPresent(p -> {
                list.add(p);
                if (!idSet.add(p.getId())) {
                    log.error("node parent ref loop, id={}", id);
                    throw ExceptionType.NODE_REF_LOOP_EXCEPTION.toException();
                }
            });

            parentId = parentNode.map(StorageNode::getParentId).orElse(null);
        }
        return list;
    }

}
