package com.test.devsu.srvclientmanager.repository;

import com.test.devsu.srvclientmanager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByDni(String dni);

    @Query(value="SELECT * FROM CLIENT WHERE client_id <> :id AND dni = :dni", nativeQuery = true)
    Optional<Client> findAnotherClientDni(Long id, String dni);
}
