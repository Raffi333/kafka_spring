package rh.example.kafka_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rh.example.kafka_spring.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
