package com.test.devsu.srvclientmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO implements Serializable {

    @NotBlank(message = "The field name is mandatory")
    private String name;

    private Character gender;

    @Min(value = 18, message = "The min age to register is 18 years.")
    @Max(value = 120, message = "The max age to register is 120 years")
    private Integer age;

    @NotBlank(message = "The field dni is mandatory")
    private String dni;

    private String address;

    private String phoneNumber;

    private String password;

    private Boolean status;

}
