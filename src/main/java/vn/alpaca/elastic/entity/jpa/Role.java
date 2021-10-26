package vn.alpaca.elastic.entity.jpa;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "user_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {

    @Id
    @SequenceGenerator(
            name = "roles_id_seq",
            sequenceName = "roles_id_seq",
            initialValue = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roles_id_seq"
    )
    private int id;

    private String name;

    @ToString.Exclude
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JoinTable(name = "roles_authorities", schema = "user_management",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;
}
