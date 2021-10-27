package vn.alpaca.elastic.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ErrorResponse {

    @JsonProperty("status")
    private int errorCode;

    private String message;

    private final Date timestamp = new Date();

    private Map<String, Object> errors;
}
