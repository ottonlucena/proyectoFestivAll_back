package com.proyectoFestivAll.proyectoFestivAll.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = true)
public class ConversionLista implements AttributeConverter<List<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<String> strings) {

        try {
            return objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException err) {
            throw new IllegalArgumentException("Error al convertir de lista a JSON", err);
        }
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException err) {
            throw new IllegalArgumentException("Error al convertir de JSON a lista", err);
        }
    }
}
