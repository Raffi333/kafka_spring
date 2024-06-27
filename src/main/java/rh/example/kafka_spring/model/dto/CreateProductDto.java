package rh.example.kafka_spring.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CreateProductDto {
    private String title;
    private BigDecimal price;
    private String quantity;
}
