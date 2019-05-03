package top.nintha.simplewebdisk.storage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageNode {
    public static final String ROOT_NODE_ID = "ROOT";
    @Id
    private String id;
    private String name;
    private String parentId;
    private Boolean fileFlag;
    private String token;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public Boolean getFolderFlag() {
        return fileFlag == null ? null : !fileFlag;
    }

    public String getCreateTimeFormat() {
        return createTime.toString();
    }

    public String getModifyTimeFormat() {
        return modifyTime.toString();
    }
}
