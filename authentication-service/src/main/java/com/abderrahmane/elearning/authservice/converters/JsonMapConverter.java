package com.abderrahmane.elearning.authservice.converters;

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

public class JsonMapConverter implements HttpMessageConverter<Map<String, Object>> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return mediaType != null && mediaType.equals(MediaType.APPLICATION_JSON) && clazz.equals(Map.class);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (mediaType == null) return Map.class.isAssignableFrom(clazz);
        return mediaType != null && mediaType.equals(MediaType.APPLICATION_JSON) && Map.class.isAssignableFrom(clazz);
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
            Map<String, Object> parsedData = objectMapper.readValue(inputMessage.getBody(), Map.class);
            return parsedData;
        } catch (Exception ex) {
            // TODO : Must throw invalid format Error
            System.err.println("[JSON-PARSING] " + ex.getMessage());
        }

        return null;
    }

    @Override
    public void write(Map<String, Object> t, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        try {
            String jsonData = objectMapper.writeValueAsString(t);
            outputMessage.getBody().write(jsonData.getBytes());
        } catch (Exception ex) {
            System.err.println("[JSON-Serializer] " + ex.getMessage());
        }
    }
    
}
