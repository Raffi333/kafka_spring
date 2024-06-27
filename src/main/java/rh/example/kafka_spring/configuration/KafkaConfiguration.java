package rh.example.kafka_spring.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic newTopic(){
        return TopicBuilder.name("product-created-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas","2"))
                .build();
    }

}
