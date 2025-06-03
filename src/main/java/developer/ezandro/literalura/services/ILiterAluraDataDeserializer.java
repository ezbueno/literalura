package developer.ezandro.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ILiterAluraDataDeserializer {
    <T> T getData(String json, Class<T> clazz) throws JsonProcessingException;
}