package com.sujith.project.dao;

import com.sujith.project.entity.*;

import java.util.*;


public interface AddressDao {
    List<Address> findAllAddress();

    Address insert(Address address);
}
