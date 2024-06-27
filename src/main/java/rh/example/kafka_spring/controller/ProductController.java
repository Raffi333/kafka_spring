package rh.example.kafka_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rh.example.kafka_spring.model.dto.CreateProductDto;
import rh.example.kafka_spring.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createProduct(@RequestBody CreateProductDto productDto) {
        String productId = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

}
