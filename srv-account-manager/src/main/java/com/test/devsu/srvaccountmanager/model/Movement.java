package com.test.devsu.srvaccountmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Transactional
public class Movement implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private BigDecimal value;
    private BigDecimal balance;
    @Column(name = "create_at")
    private Date date;
    @Column(name = "account_id")
    private Long accountId;

}
