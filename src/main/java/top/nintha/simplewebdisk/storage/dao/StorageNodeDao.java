package top.nintha.simplewebdisk.storage.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.nintha.simplewebdisk.storage.entity.StorageNode;

import java.util.List;

public interface StorageNodeDao extends MongoRepository<StorageNode, String> {
    List<StorageNode> findByParentId(String parentId);

    long countByParentId(String parentId);
}
