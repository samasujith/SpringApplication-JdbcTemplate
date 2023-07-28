package com.sujith.project.dao;

import com.sujith.project.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class AddressDaoImpl implements AddressDao {
    JdbcTemplate jdbcTemplate;


    @Autowired
    public AddressDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper<Address> addressRowMapper = (rs, rowNum) -> {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        address.setPin(rs.getInt("pincode"));
        return address;
    };

    @Override
    public List<Address> findAllAddress() {
        //        TypedQuery<Address> tempAddress = entityManager.createQuery("from Address ", Address.class);
        //        return tempAddress.getResultList();
        String query="select * from address";
        return jdbcTemplate.query(query,addressRowMapper);
    }




}
