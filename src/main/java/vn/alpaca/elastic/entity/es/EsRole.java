package vn.alpaca.elastic.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import vn.alpaca.elastic.entity.jpa.Authority;

import javax.persistence.Id;
import java.util.Set;

@Document(indexName = "roles")
@Data
public class EsRole {

    @Id
    private int id;

    private String name;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Set<Authority> authorities;
}
