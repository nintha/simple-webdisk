package top.nintha.simplewebdisk.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "webdisk")
public class WebdiskProps {
    private String defaultUsername;
    private String defaultPassword;
}
