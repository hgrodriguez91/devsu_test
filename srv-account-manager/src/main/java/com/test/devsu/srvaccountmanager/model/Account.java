package com.test.devsu.srvaccountmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Account implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private Long clientId;
    private String type;
    private BigDecimal balance;
    private Boolean status;
    @OneToMany(mappedBy="accountId", fetch = FetchType.EAGER)
    private List<Movement> movements;

}
