package com.abderrahmane.elearning.socialservice.converters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;


public class JsonMapHttpMessageConverter implements HttpMessageConverter<Map<String, Object>> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.equals(Map.class) && mediaType.equals(MediaType.APPLICATION_JSON);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (mediaType == null) return Map.class.isAssignableFrom(clazz);
        return mediaType.equals(MediaType.APPLICATION_JSON) && Map.class.isAssignableFrom(clazz);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.APPLICATION_JSON);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> read(Class<? extends Map<String, Object>> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        try {
            return objectMapper.readValue(inputMessage.getBody(), Map.class);
        } catch (Exception ex) {
            throw new HttpMessageNotReadableException("invalid_json_format", inputMessage);
        }
    }

    @Override
    public void write(Map<String, Object> t, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        try {
            outputMessage.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            outputMessage.getBody().write(objectMapper.writeValueAsString(t).getBytes());
        } catch (Exception ex) {
            System.out.println("[ERROR-JSON] " + ex.getMessage());
        }
    }
    
}
