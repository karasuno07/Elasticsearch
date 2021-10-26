package vn.alpaca.elastic.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserResponse {

    private int id;

    private String username;

    private String password;

    private String fullName;

    private boolean gender;

    @JsonProperty("id_card_number")
    private String idCardNumber;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("phone_numbers")
    private Set<String> phoneNumbers;

    private String email;

    private String address;

    private RoleResponse role;
}
