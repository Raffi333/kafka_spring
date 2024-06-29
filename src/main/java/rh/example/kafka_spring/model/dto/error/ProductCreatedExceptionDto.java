package rh.example.kafka_spring.model.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ProductCreatedExceptionDto {

    private Date timestamp;
    private Integer statusCode;
    private String message;
}
