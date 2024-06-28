package rh.example.kafka_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rh.example.kafka_spring.model.dto.CreateProductDto;
import rh.example.kafka_spring.service.ProductService;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDto productDto) {
        String productId = null;
        try {
            productId = productService.createProduct(productDto);
        } catch (ExecutionException | InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

}
