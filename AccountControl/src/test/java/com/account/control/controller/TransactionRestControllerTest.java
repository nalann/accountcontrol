package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.exception.NotEnoughBalanceException;
import com.account.control.model.dto.CustomerDto;
import com.account.control.model.dto.TransactionDto;
import com.account.control.model.request.NewTransactionRequest;
import com.account.control.service.CustomerService;
import com.account.control.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class TransactionRestControllerTest {

    private final LocalDateTime currentDate = LocalDateTime.now();

    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    TransactionService transactionService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private NewTransactionRequest newTransactionRequest = null;

    private TransactionDto transactionDto = null;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        newTransactionRequest = NewTransactionRequest.builder()
                .transactionAmount(BigDecimal.valueOf(100.0))
                .customerId(1)
                .detail("test")
                .build();

        transactionDto = TransactionDto.builder()
                .id(1)
                .transactionDate(currentDate)
                .transactionDetail(newTransactionRequest.getDetail())
                .transactionAmount(newTransactionRequest.getTransactionAmount())
                .build();

    }

    @Test
    public void testNotEnoughBalanceException() throws Exception{
        Mockito.when(transactionService.createNewTransaction(newTransactionRequest))
                .thenThrow(new NotEnoughBalanceException("Not Enough Balance for customer: 1"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/transaction/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newTransactionRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void testCustomerNotFoundException() throws Exception{
        Mockito.when(transactionService.createNewTransaction(newTransactionRequest))
                .thenThrow(new CustomerNotFoundException("Customer Not Found: 1"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/transaction/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newTransactionRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void testInternalServerException() throws Exception{
        Mockito.when(transactionService.createNewTransaction(newTransactionRequest))
                .thenThrow(new RuntimeException());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/transaction/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newTransactionRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError()).andReturn();
    }

    @Test
    public void testNewTransaction() throws Exception{
        Mockito.when(transactionService.createNewTransaction(newTransactionRequest)).thenReturn(transactionDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/transaction/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newTransactionRequest));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertNotNull(result);
        String json = result.getResponse().getContentAsString();
        Assertions.assertEquals(mapper.writeValueAsString(transactionDto), json);
    }
}
