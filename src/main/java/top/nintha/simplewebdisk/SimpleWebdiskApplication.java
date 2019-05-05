package top.nintha.simplewebdisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
@EnableConfigurationProperties
public class SimpleWebdiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleWebdiskApplication.class, args);
    }

}
