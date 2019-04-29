package top.nintha.simplewebdisk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
public class GridFsConfiguration {

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDbFactory mongoDbFactory, MappingMongoConverter mappingMongoConverter) {
        return new GridFsTemplate(mongoDbFactory, mappingMongoConverter);
    }

    @Bean
    public DefaultDataBufferFactory defaultDataBufferFactory(){
        return new DefaultDataBufferFactory();
    }

}