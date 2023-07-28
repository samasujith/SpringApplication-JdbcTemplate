package com.sujith.project.service;

import com.sujith.project.dao.*;
import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpa.*;
import com.sujith.project.jpaservice.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressServiceTest {
    static List<Address> validAddresses = new ArrayList<>();
    static Address validAddress = Address.builder().id(1).pin(507115).street("ambedker").city("pvc").build();
    static List<Address> invalidAddresses = new ArrayList<>();
    static Address invalidAddress = new Address();
    @MockBean
    AddressDao addressDao;
    @MockBean
    AddressJpa addressJpa;
    @Autowired
    AddressJpaService addressService;
    @Autowired
    MockMvc mockMvc;

    @BeforeAll
    static void beforeAll() {

        validAddresses.add(Address.builder().id(1).pin(507115).street("ambedker").city("pvc").build());
        validAddresses.add(Address.builder().id(2).pin(500100).street("hitech city").city("hyderabad").build());
        invalidAddresses.add(new Address());
        invalidAddresses.add(new Address());
    }

    @Test
    @DisplayName("findAllAddress_valid")
    void findAllAddress_valid() {
        when(addressJpa.findAll()).thenReturn(validAddresses);
        assertEquals(validAddresses, addressService.findAllAddress());
    }

    @Test
    @DisplayName("findAllAddress_throwApiRequestException")
    void findAllAddress_throwApiRequestException() {
        when(addressJpa.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ApiRequestException.class, () -> addressService.findAllAddress());
    }

    @Test
    @DisplayName("insertAddress_valid")
    void insertAddress_valid() {
        when(addressDao.insert(validAddress)).thenReturn(validAddress);
        assertEquals(validAddress, addressService.insert(validAddress));
    }

    @Test
    @DisplayName("insertAddress_throwApiRequestException")
    void insertAddress_throwApiRequestException() {
        when(addressDao.insert(invalidAddress)).thenThrow(ApiRequestException.class);
        assertThrows(ApiRequestException.class, () -> addressService.insert(invalidAddress));
    }
}
