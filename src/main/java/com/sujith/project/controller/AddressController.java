package com.sujith.project.controller;

import com.sujith.project.jpaservice.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController

@RequestMapping("/api")

public class AddressController {
    AddressJpaService addressService;

    @Autowired
    public AddressController(AddressJpaService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public List<com.sujith.project.entity.Address> findAddress() {
        return addressService.findAllAddress();
    }


}
