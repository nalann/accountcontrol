package com.account.control.controller;

import com.account.control.exception.CustomerNotFoundException;
import com.account.control.model.dto.CustomerAccountDto;
import com.account.control.model.dto.CustomerDto;
import com.account.control.model.dto.TransactionDto;
import com.account.control.service.CustomerService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class CustomerRestControllerTest {

    private final LocalDateTime currentDate = LocalDateTime.now();

    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    CustomerService customerService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private CustomerDto customerDto = null;

    private List<CustomerDto> customerDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        TransactionDto transactionDto = TransactionDto.builder()
                .id(1)
                .transactionAmount(BigDecimal.valueOf(10.0))
                .transactionDetail("test")
                .transactionDate(currentDate)
                .build();

        Set<TransactionDto> transactionDtos = new HashSet<>();
        transactionDtos.add(transactionDto);

        CustomerAccountDto customerAccountDto = CustomerAccountDto.builder()
                .id(1)
                .creationDate(currentDate)
                .transactions(transactionDtos)
                .build();

        Set<CustomerAccountDto> customerAccountDtos = new HashSet<>();
        customerAccountDtos.add(customerAccountDto);

        customerDto = CustomerDto.builder()
                .id(1)
                .name("Name")
                .surname("Surname")
                .balance(BigDecimal.valueOf(100.50))
                .accounts(customerAccountDtos)
                .build();

        customerDtoList.add(customerDto);

        CustomerDto customerDto1 = CustomerDto.builder()
                .id(2)
                .name("Test")
                .surname("Data")
                .balance(BigDecimal.valueOf(0.0))
                .accounts(null)
                .build();

        customerDtoList.add(customerDto1);

    }

    @Test
    public void testGetAllCustomer() throws Exception{
        Mockito.when(customerService.findAllCustomers()).thenReturn(customerDtoList);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/customer/all")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andReturn();

        Assertions.assertNotNull(result);
        String json = result.getResponse().getContentAsString();
        Assertions.assertEquals(mapper.writeValueAsString(customerDtoList), json);
    }

    @Test
    public void testGetCustomerInternalServer() throws Exception{
        Mockito.when(customerService.findAllCustomers()).thenThrow(new NullPointerException());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/customer/all")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError()).andReturn();
    }

    @Test
    public void testGetCustomerById() throws Exception{
        Mockito.when(customerService.findCustomer(1)).thenReturn(customerDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/customer/1")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(mockRequest)
                .andExpect(status().isOk()).andReturn();

        Assertions.assertNotNull(result);
        String json = result.getResponse().getContentAsString();
        Assertions.assertEquals(mapper.writeValueAsString(customerDto), json);
    }

    @Test
    public void testGetCustomerByIdNoFound() throws Exception{
        Mockito.when(customerService.findCustomer(1)).thenThrow(new CustomerNotFoundException(""));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/customer/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent()).andReturn();
    }

    @Test
    public void testGetCustomerByIdInternalServer() throws Exception{
        Mockito.when(customerService.findCustomer(1)).thenThrow(new RuntimeException());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/customer/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isInternalServerError()).andReturn();
    }

}
