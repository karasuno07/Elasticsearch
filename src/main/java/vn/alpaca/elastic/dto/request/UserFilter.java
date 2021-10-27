package vn.alpaca.elastic.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserFilter {

    private String username = "";

    @JsonProperty("full_name")
    private String fullName = "";

    private Boolean gender = null;

    @JsonProperty("id_card_number")
    private String idCardNumber = "";

    @JsonProperty("dob_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date from = new Date(Long.MIN_VALUE);

    @JsonProperty("dob_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date to = new Date(Long.MAX_VALUE);

    @JsonProperty("phone_number")
    private String phoneNumber = "";

    private String email = "";

    private String address = "";

    private Boolean active = null;

}