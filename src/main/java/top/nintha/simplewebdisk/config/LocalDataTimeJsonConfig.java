package top.nintha.simplewebdisk.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@JsonComponent
public class LocalDataTimeJsonConfig {

    public static class LocalDateTimeJsonSerializer extends  JsonSerializer<LocalDateTime> {

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeNumber(value.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli() + "");
        }
    }

}