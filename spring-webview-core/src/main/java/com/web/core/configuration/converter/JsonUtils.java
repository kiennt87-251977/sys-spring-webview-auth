package com.web.core.configuration.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.TimeZone;

@UtilityClass
public class JsonUtils {

    private static class InstanceHolder {
        private static final ObjectMapper INSTANCE = JsonMapper.builder()
                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
                .defaultTimeZone(TimeZone.getDefault())
                .addModule(new JavaTimeModule())
                .addModule(new SimpleModule().addDeserializer(String.class, new StringWithoutSpaceDeserializer(String.class)))
                .build();

        private static class StringWithoutSpaceDeserializer extends StdDeserializer<String> {

            public StringWithoutSpaceDeserializer(Class<String> vc) {
                super(vc);
            }

            @Override
            public String deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
                return StringUtils.trim(p.getText());
            }
        }
    }

    public static ObjectMapper getObjectMapperInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return getObjectMapperInstance().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format("Exception when parsing [JSON=%s] to %s", json, clazz.getSimpleName()), e);
        }
    }

    public static <T> String toJson(T data) {
        try {
            return getObjectMapperInstance().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format("Exception when parsing [DATA=%s] to JSON", data), e);
        }
    }

}
