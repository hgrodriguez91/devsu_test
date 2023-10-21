package com.test.devsu.srvclientmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovementDTO {
    private String type;
    private BigDecimal value;
    private BigDecimal balance;
    private Date date;
}
