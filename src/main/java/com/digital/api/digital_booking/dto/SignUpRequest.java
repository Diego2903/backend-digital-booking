package com.digital.api.digital_booking.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    @NotEmpty
    @Length(min = 4,max = 50)
    private String email;

    @NotEmpty(message = "Invalid Name: Empty name")
    @Length(min = 4,max = 50)
    private String name;


    @NotEmpty
    @Length(min = 4,max = 50)
    private String password;

}
