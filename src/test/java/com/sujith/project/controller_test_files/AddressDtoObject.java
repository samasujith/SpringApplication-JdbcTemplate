package com.sujith.project.controller_test_files;

import com.sujith.project.entity.*;

import java.util.*;

public class AddressDtoObject {
    public static String getAddressJson() {
        return """
                                
                    {
                        "id": 1,
                        "street": "123 Main Street",
                        "city": "New York",
                        "pin": 10001
                    }
                                
                """;
    }

    public static List<Address> getAllAddress() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(Address.builder().id(1).pin(507115).street("ambedker").city("pvc").build());
        addresses.add(Address.builder().id(2).pin(500100).street("hitech city").city("hyderabad").build());
        return addresses;
    }

    public static List<Address> getAllInvalidAddress() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(Address.builder().build());
        addresses.add(Address.builder().build());
        return addresses;
    }
}
