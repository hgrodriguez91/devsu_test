package com.test.devsu.srvaccountmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDTO {
    private Long id;
    private String type;
    @NotNull(message = "the field value is mandatory")
    private BigDecimal value;
    private BigDecimal balance;
    private Timestamp date;
    @NotNull(message = "the field accountId is mandatory")
    private Long accountId;
}
