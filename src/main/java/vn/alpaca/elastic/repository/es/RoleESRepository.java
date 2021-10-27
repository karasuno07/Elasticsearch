package vn.alpaca.elastic.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.alpaca.elastic.entity.es.RoleES;

public interface RoleESRepository extends
        ElasticsearchRepository<RoleES, Integer> {
}
