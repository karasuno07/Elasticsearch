package vn.alpaca.userservice.dto.wrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;


@Data
@EqualsAndHashCode(callSuper = true)
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
