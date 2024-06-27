package rh.example.kafka_spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import rh.example.kafka_spring.model.Product;
import rh.example.kafka_spring.model.dto.CreateProductDto;
import rh.example.kafka_spring.repository.ProductRepository;


import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private ProductRepository productRepository;
    private KafkaTemplate<String, Product> kafkaTemplate;

    public ProductService(ProductRepository productRepository, KafkaTemplate<String, Product> kafkaTemplate) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public String createProduct(CreateProductDto productDto) {

        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepository.save(product);

        if (product.getId() != null) {
            String productId = String.valueOf(product.getId());
            CompletableFuture<SendResult<String, Product>> future =
                    kafkaTemplate.send("product-created-event-topic", productId, product);
            future.whenComplete((result, e) -> {
                if (e != null) {
                    LOGGER.error("Failed to send message :{}", e.getMessage());
                } else {
                    LOGGER.info("message send successfully :{}", result.getRecordMetadata());
                }
            });
            LOGGER.info("Return: {}", productId);
            return productId;
        }

        return null;
    }

}
