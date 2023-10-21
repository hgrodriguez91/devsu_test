package com.test.devsu.srvclientmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long clientId;
    private String type;
    private BigDecimal balance;
    private Boolean status;
    private List<MovementDTO> movements;
}
