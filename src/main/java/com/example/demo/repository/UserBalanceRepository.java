package com.example.demo.repository;

import com.example.demo.entities.Record;
import com.example.demo.entities.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance,Long> {
    UserBalance getByUserId(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE UserBalance ub " +
            "SET ub.balance = :newBalance " +
            "WHERE ub.id = :userId ")
    void updateBalanceByUserId(@Param("userId")Long userId,@Param("newBalance") BigDecimal newBalance);

}
