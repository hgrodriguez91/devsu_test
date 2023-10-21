package com.test.devsu.srvclientmanager.model;

import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Client extends Person{

    @Id
    @Column(name = "client_id", nullable= false, unique = true)
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long clientId;

    private String password;

    private Boolean status;

}
