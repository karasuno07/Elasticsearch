package vn.alpaca.elastic.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

@Document(indexName = "users")
@Data
public class EsUser {

    @Id
    private int id;

    private String username;

    private String password;

    @Field(name = "full_name")
    private String fullName;

    private boolean gender;

    @Field(name = "id_card_number")
    private String idCardNumber;

    @Field(name = "date_of_birth", type = FieldType.Date)
    private Date dateOfBirth;

    @Field(name = "phone_numbers")
    private Set<String> phoneNumbers;

    private String email;

    private String address;

    private boolean active = true;

    @Field(type = FieldType.Nested, includeInParent = true)
    private EsRole role;

}
