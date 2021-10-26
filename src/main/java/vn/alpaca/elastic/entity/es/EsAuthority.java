package vn.alpaca.elastic.entity.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;

@Document(indexName = "authorities")
@Data
public class EsAuthority {

    @Id
    private int id;

    @Field(name = "permission")
    private String permissionName;
}
