package com.hook4startup.services;


import com.hook4startup.models.User;
import com.hook4startup.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerRepo customerRepo;

@Transactional
    public void customerSave(User customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);
        System.out.println(customer);
    }

    public Optional<User> findCustomerByUserName(String userName) {

      return customerRepo.findByUsername(userName);

      }

    public List<User> findAllCustomer() {

      return customerRepo.findAll();
    }


}




