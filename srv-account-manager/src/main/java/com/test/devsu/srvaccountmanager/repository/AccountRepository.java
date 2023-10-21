package com.test.devsu.srvaccountmanager.repository;

import com.test.devsu.srvaccountmanager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAccountByClientId(Long clientId);

    @Query(value="SELECT * FROM account where id <> :id AND client_id = :clientId AND type like %:type% limit 1", nativeQuery= true)
    Optional<Account> findAccountByClientIdAndType(Long id, Long clientId, String type );

    @Query(value="SELECT * FROM account where  client_id = :clientId AND type like %:type% limit 1", nativeQuery= true)
    Optional<Account> findAccountByOldType( Long clientId, String type );
}
