package rh.example.kafka_spring.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import rh.example.kafka_spring.model.Product;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapService;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.properties.delivery.timeout.ms}")
    private String deliveryTimeout;
    @Value("${spring.kafka.producer.properties.linger.ms}")
    private String propertiesLinger;
    @Value("${spring.kafka.producer.properties.request.timeout.ms}")
    private String requestTimeout;

    private Map<String, Object> getKafkaConfigProperties() {
        Map<String, Object> conf = new HashMap<>();
        conf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapService);
        conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        conf.put(ProducerConfig.ACKS_CONFIG, acks);
        conf.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, deliveryTimeout);
        conf.put(ProducerConfig.LINGER_MS_CONFIG, propertiesLinger);
        conf.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, requestTimeout);
        return conf;
    }

    @Bean
    public ProducerFactory<String, Product> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getKafkaConfigProperties());
    }

    @Bean
    public KafkaTemplate<String, Product> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name("product-created-event-topic")
                .partitions(3)
                .replicas(3)
                .configs(Map.of("min.insync.replicas", "2"))
                .build();
    }

}
