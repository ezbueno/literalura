package developer.ezandro.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import developer.ezandro.literalura.exceptions.DataDeserializationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LiterAluraDataDeserializer implements ILiterAluraDataDeserializer {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clazz) {
        try {
            if (json == null || json.trim().isEmpty()) {
                throw new IOException("Empty or null JSON received from API.");
            }

            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new DataDeserializationException("Error parsing JSON data", e);
        } catch (IOException e) {
            throw new DataDeserializationException("IO error while reading JSON data", e);
        }
    }
}