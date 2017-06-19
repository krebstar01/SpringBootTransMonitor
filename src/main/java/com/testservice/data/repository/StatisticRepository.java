package com.testservice.data.repository;

import com.testservice.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by justin on 15/02/2017.
 */
@Repository
public interface StatisticRepository extends JpaRepository<Transaction, Long> {


    /**
     * l count is a long specifiying the total number of transactions happened in the last 60 seconds
     */
    public Long countByTimestampBetween(LocalDateTime from, LocalDateTime to);


    /**
     * l max is a double specifiying single highest transaction value in the last 60 seconds
     */
    @Query("SELECT MAX(T.amount) FROM Transaction T WHERE T.timestamp BETWEEN ?1 AND ?2")
    public Double maxAmountByTimestampBetween(LocalDateTime from, LocalDateTime to);


    /**
     * l min is a double specifiying single lowest transaction value in the last 60 seconds
     */
    @Query("SELECT MIN(T.amount) FROM Transaction T WHERE T.timestamp BETWEEN ?1 AND ?2")
    public Double minAmountByTimestampBetween(LocalDateTime from, LocalDateTime to);


    /**
     * l avg is a double specifiying the average amount of transaction value in the last 60 seconds
     */
    @Query("SELECT AVG(T.amount) FROM Transaction T WHERE T.timestamp BETWEEN ?1 AND ?2")
    public Double avgAmountByTimestampBetween(LocalDateTime from, LocalDateTime to);


    /**
     * l sum is a double specifiying the total sum of transaction value in the last 60 seconds
     */
    @Query("SELECT SUM(T.amount) FROM Transaction T WHERE T.timestamp BETWEEN ?1 AND ?2")
    public Double sumAmountByTimestampBetween(LocalDateTime from, LocalDateTime to);

}


