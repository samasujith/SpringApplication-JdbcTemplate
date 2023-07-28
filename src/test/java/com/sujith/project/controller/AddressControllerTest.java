package com.sujith.project.controller;


import com.sujith.project.controller_test_files.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpaservice.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {


    @Autowired
    AddressController addressController;

    @MockBean
    AddressJpaService addressService;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("getAllAddress_valid")
    void getAllAdddress_valid() throws Exception {
        when(addressService.findAllAddress()).thenReturn(AddressDtoObject.getAllAddress());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/address"))
                .andExpect(status().isOk()).andExpect(result -> {
                    AddressDtoObject.getAllAddress();
                });
    }

    @Test
    @DisplayName("getAllAddress_notValid")
    void getAllAdddress_notValid() throws Exception {
        when(addressService.findAllAddress()).thenThrow(new ApiRequestException("No Addresses were found"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/address")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No Addresses were found"));
    }


}
