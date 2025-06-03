package developer.ezandro.literalura.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ApiResponseDTO(
        int count,
        List<BookDTO> results) {
}