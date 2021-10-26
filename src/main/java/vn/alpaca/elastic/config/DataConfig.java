package vn.alpaca.elastic.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableElasticsearchRepositories(
        basePackages = "vn.alpaca.elastic.repository.es"
)
@EnableJpaRepositories(
        basePackages = "vn.alpaca.elastic.repository.jpa"
)
public class DataConfig {

    @Bean
    public RestHighLevelClient restClient() {
        ClientConfiguration configuration
                = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();
        return RestClients.create(configuration).rest();
    }

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchOperations esTemplate() {
        return new ElasticsearchRestTemplate(restClient());
    }
}
