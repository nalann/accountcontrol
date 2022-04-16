package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.model.dto.AccountCustomerDto;
import com.account.control.model.dto.AccountDto;
import com.account.control.model.request.CreateAccountRequest;
import com.account.control.service.AccountService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class AccountRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    AccountService accountService;

    private final LocalDateTime currentDate = LocalDateTime.now();

    private AccountDto accDto1 = null;

    private AccountCustomerDto accCustomer = null;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        accCustomer = AccountCustomerDto.builder().id(1)
                .name("Name")
                .surname("Surname")
                .build();
        accDto1 = AccountDto.builder().id(1)
                .creationDate(currentDate)
                .initialCredit(BigDecimal.valueOf(25.0))
                .transactions(null)
                .customer(accCustomer)
                .build();
    }

    @Test
    public void testAddAccount() throws Exception {

        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .initialCredit(BigDecimal.valueOf(25.0)).customerId(1).build();

        Mockito.when(accountService.createNewAccount(createAccountRequest)).thenReturn(accDto1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createAccountRequest));

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isCreated()).andReturn();

        Assertions.assertNotNull(result);
        String json = result.getResponse().getContentAsString();
        Assertions.assertEquals(mapper.writeValueAsString(accDto1), json);
    }

    @Test
    public void testBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new CreateAccountRequest()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    public void testCustomerNotFound() throws Exception {
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .initialCredit(BigDecimal.valueOf(25.0)).customerId(2).build();

        Mockito.when(accountService.createNewAccount(createAccountRequest)).thenThrow(new CustomerNotFoundException(""));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createAccountRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent()).andReturn();

    }

    @Test
    public void testInternalServerError() throws Exception {
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .initialCredit(BigDecimal.valueOf(25.0)).customerId(2).build();

        Mockito.when(accountService.createNewAccount(createAccountRequest)).thenThrow(new NullPointerException());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/account/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createAccountRequest));

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError()).andReturn();

    }
}
