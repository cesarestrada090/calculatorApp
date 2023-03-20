package com.example.demo.service.impl;

import com.example.demo.entities.OperationType;
import com.example.demo.entities.Record;
import com.example.demo.entities.UserBalance;
import com.example.demo.repository.OperationRepository;
import com.example.demo.repository.RecordRepository;
import com.example.demo.repository.UserBalanceRepository;
import com.example.demo.service.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RecordServiceImpl implements RecordService {

    final RecordRepository recordRepository;
    final UserBalanceRepository userBalanceRepository;
    final OperationRepository operationRepository;
    public RecordServiceImpl(RecordRepository recordRepository,
                             UserBalanceRepository userBalanceRepository,
                             OperationRepository operationRepository) {
        this.recordRepository = recordRepository;
        this.userBalanceRepository = userBalanceRepository;
        this.operationRepository = operationRepository;
    }

    public BigDecimal getOperationCost(Long operationId) {
        OperationType operation = operationRepository.getReferenceById(operationId);
        return operation.getCost();
    }

    public BigDecimal getBalanceByUser(Long userId) {
        UserBalance userBalance = userBalanceRepository.getByUserId(userId);
        return userBalance.getBalance();
    }

    @Override
    public Record saveRecord(Record record) throws IllegalAccessException {
        BigDecimal operationCost = getOperationCost(record.getOperationId());
        BigDecimal userBalance = getBalanceByUser(record.getUserId());
        if(userBalance.compareTo(operationCost) >= 0){
            userBalanceRepository.updateBalanceByUserId(record.getUserId(),userBalance.subtract(operationCost));
            record.setAmount(operationCost);
            record.setUserBalance(userBalance.subtract(operationCost));
            record.setOperationResponse(HttpStatus.OK.value());
        } else {
            record.setOperationResponse(HttpStatus.BAD_REQUEST.value());
            throw new IllegalAccessException("User’s balance isn’t enough to cover the request cost");
        }
        return recordRepository.save(record);
    }
}
