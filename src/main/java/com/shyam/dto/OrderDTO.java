package com.shyam.dto;

import com.shyam.validator.Price;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotBlank(message = "select mode of payment")
    private String modeOfPayment;

    @Price(message = "Quantity must be grater than zero")
    private double quantity;
}
