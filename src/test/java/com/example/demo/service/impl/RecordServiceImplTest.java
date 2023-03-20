package com.example.demo.service.impl;

import com.example.demo.controller.CalculatorController;
import com.example.demo.entities.OperationType;
import com.example.demo.entities.Record;
import com.example.demo.entities.UserBalance;
import com.example.demo.repository.OperationRepository;
import com.example.demo.repository.RecordRepository;
import com.example.demo.repository.UserBalanceRepository;
import com.example.demo.service.RecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
class RecordServiceImplTest {

    @Autowired
    RecordService recordService;

    @MockBean
    private UserBalanceRepository userBalanceRepository;

    @MockBean
    private RecordRepository recordRepository;

    @MockBean
    private OperationRepository operationRepository;

    @Bean
    public RecordService recordService() {
        return Mockito.mock(RecordService.class);
    }

    @Test
    void testCostGreaterThanBalance(){
        // Setup our record to save
        Record record = new Record();
        record.setAmount(new BigDecimal(33));
        record.setId(1l);

        //Configure mock User Balance with 50
        UserBalance userBalance = new UserBalance();
        userBalance.setBalance(new BigDecimal(50));
        userBalance.setId(1l);
        doReturn(userBalance).when(userBalanceRepository).getByUserId(any());


        //Set Operation cost of 60
        OperationType operationType = new OperationType();
        operationType.setId(1l);
        operationType.setCost(new BigDecimal(60));
        doReturn(operationType).when(operationRepository).getReferenceById(any());

        // "Illegal Access Exception should be thrown because balance is not greater than operation cost"
        IllegalAccessException thrown = Assertions.assertThrows(IllegalAccessException.class, () -> {
            recordService.saveRecord(record);
        }, "User’s balance isn’t enough to cover the request cost");
    }

    @Test
    void testCostLowerThanBalance() throws IllegalAccessException {
        // Setup our record to save
        Record record = new Record();
        record.setAmount(new BigDecimal(33));
        record.setId(1l);

        //Configure mock User Balance with 50
        UserBalance userBalance = new UserBalance();
        userBalance.setBalance(new BigDecimal(50));
        userBalance.setId(1l);
        doReturn(userBalance).when(userBalanceRepository).getByUserId(any());


        //Set Operation cost of 10
        OperationType operationType = new OperationType();
        operationType.setId(1l);
        operationType.setCost(new BigDecimal(10));
        doReturn(operationType).when(operationRepository).getReferenceById(any());

        // "Illegal Access Exception should be thrown because balance is not greater than operation cost"
        doNothing().when(userBalanceRepository).updateBalanceByUserId(any(),any());

        recordService.saveRecord(record);
        // User balance should by decreased by 10
        assertEquals(record.getUserBalance(), new BigDecimal(40));
    }


}