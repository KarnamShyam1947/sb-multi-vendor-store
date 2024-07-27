package com.shyam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @NotBlank(message = "Conform Password is Required")
    private String conformPassword;

    @NotBlank(message = "Phone Number is Required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Enter a valid phone number")
    private String phoneNumber;
    
    @NotBlank(message = "Username is Required")
    private String username;
    
    @NotBlank(message = "Password is Required")
    private String password;

    @NotBlank(message = "Email is Required")
    private String email;

    @NotBlank(message = "Address is Required")
    private String address;
    
}
