package vn.alpaca.elastic.entity.jpa;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", schema = "user_management")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq",
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq"
    )
    private int id;

    private String username;

    private String password;

    private String fullName;

    private boolean gender;

    private String idCardNumber;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Type(type = "list-array")
    @Column(columnDefinition = "text[]")
    private List<String> phoneNumbers;

    private String email;

    private String address;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
