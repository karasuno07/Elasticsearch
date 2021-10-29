package vn.alpaca.userservice.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"status_code", "message", "timestamp", "errors"})
public final class ErrorResponse extends AbstractResponse {

    private Map<String, String> errors;
}
