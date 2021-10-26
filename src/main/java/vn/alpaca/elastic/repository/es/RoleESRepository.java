package vn.alpaca.elastic.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.alpaca.elastic.entity.es.EsRole;

public interface RoleESRepository extends
        ElasticsearchRepository<EsRole, Integer> {
}
