package com.test.devsu.srvaccountmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    @JsonIgnore
    private Long id;
    @NotNull(message = "the field clienId is mandatory")
    private Long clientId;
    @NotBlank(message = "the field type is mandatory")
    private String type;
    @NotNull(message = "the field balance is mandatory")
    private BigDecimal balance;
    @NotNull(message = "the field status is mandatory")
    private Boolean status;
    @JsonIgnore
    private Set<MovementDTO> movements;
}
