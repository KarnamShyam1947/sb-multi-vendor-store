package com.shyam.dto;

import org.springframework.web.multipart.MultipartFile;

import com.shyam.validator.FileRequired;
import com.shyam.validator.Price;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotBlank(message = "name is required")
    private String name;

    @Price()
    private double totalPrice;

    @FileRequired
    private MultipartFile file;

    @NotBlank(message = "description is required")
    private String description; 
}

