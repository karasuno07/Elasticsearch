package vn.alpaca.userservice.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"status_code", "message", "timestamp", "data"})
public final class SuccessResponse<S> extends AbstractResponse {

    private S data;

    public SuccessResponse(S data) {
        super(HttpStatus.OK.value());
        this.data = data;
    }

    public SuccessResponse(int statusCode, S data) {
        super(statusCode);
        this.data = data;
    }
}
