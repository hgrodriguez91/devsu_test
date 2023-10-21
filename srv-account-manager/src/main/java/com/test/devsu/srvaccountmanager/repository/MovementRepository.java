package com.test.devsu.srvaccountmanager.repository;

import com.test.devsu.srvaccountmanager.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    /*List<Movement> findAllByAccount_Id(Long accountId);*/

    @Query(value="SELECT  * FROM movement where account_id = :accountId ORDER BY create_at DESC LIMIT 1", nativeQuery=true)
    Optional<Movement>findAccountBalance(Long accountId);

    @Query(value="SELECT * FROM movement WHERE (create_at BETWEEN :initialDate AND :finalDate) AND account_id = :accountId", nativeQuery=true)
    List<Movement> findMovementInRangeByAccountId(Long accountId,String initialDate, String finalDate);

}
