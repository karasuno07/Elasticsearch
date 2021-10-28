package vn.alpaca.userservice.dto.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public final class ErrorResponse extends AbstractResponse {

    private Map<String, Object> errors;
}
