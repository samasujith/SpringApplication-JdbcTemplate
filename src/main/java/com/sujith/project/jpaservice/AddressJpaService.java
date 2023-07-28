package com.sujith.project.jpaservice;

import com.sujith.project.dao.*;
import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpa.*;
import jakarta.transaction.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service

public class AddressJpaService {
    AddressJpa addressJpa;
    AddressDao addressDao;

    @Autowired
    public AddressJpaService(AddressJpa addressJpa, AddressDao addressDao) {
        this.addressJpa = addressJpa;
        this.addressDao = addressDao;
    }

    public List<Address> findAllAddress() {
        List<Address> addresses = addressJpa.findAll();
        if (addresses.isEmpty()) {
            throw new ApiRequestException("No Addresses were found");
        } else {
            return addresses;
        }
    }


    @Transactional
    public Address insert(Address address) {
        return addressDao.insert(address);
    }
}
