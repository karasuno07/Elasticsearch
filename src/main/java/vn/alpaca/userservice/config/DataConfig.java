package vn.alpaca.userservice.config;

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
        basePackages = "vn.alpaca.userservice.repository.es"
)
@EnableJpaRepositories(
        basePackages = "vn.alpaca.userservice.repository.jpa"
)
public class DataConfig {

    @Bean
    public RestHighLevelClient restClient() {
        final ClientConfiguration configuration
                = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withBasicAuth("elastic", "123456")
                .build();
        return RestClients.create(configuration).rest();
    }

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchOperations esTemplate() {
        return new ElasticsearchRestTemplate(restClient());
    }
}
