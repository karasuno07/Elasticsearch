package vn.alpaca.elastic.entity.jpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authorities", schema = "user_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority {

    @Id
    @SequenceGenerator(
            name = "authorities_id_seq",
            sequenceName = "roles_id_seq",
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "authorities_id_seq"
    )
    private int id;

    private String permissionName;
}
