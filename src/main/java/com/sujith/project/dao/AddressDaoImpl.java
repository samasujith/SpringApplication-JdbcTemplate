package com.sujith.project.dao;

import com.sujith.project.entity.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class AddressDaoImpl implements AddressDao {
    EntityManager entityManager;

    @Autowired
    public AddressDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Address> findAllAddress() {
        TypedQuery<Address> tempAddress = entityManager.createQuery("from Address ", Address.class);
        return tempAddress.getResultList();
    }

    @Override
    public Address insert(Address address) {
        entityManager.persist(address);
        return address;
    }


}
