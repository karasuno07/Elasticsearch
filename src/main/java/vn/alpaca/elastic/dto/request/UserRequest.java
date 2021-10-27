package vn.alpaca.elastic.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserRequest {

    private String username;

    private String password;

    @JsonProperty("full_name")
    private String fullName;

    private boolean gender;

    @JsonProperty("id_card_number")
    private String idCardNumber;

    @JsonProperty("date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @JsonProperty("phone_numbers")
    private Set<String> phoneNumbers;

    private String email;

    private String address;

    @JsonProperty("role_id")
    private Integer roleId;
}
