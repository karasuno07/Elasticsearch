package vn.alpaca.elastic.repository.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import vn.alpaca.elastic.entity.es.EsUser;

public interface UserESRepository extends
        ElasticsearchRepository<EsUser, Integer> {
}