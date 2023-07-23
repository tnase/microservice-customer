package com.tmsService.customer.Dao;

import com.tmsService.customer.Entities.CustomerEntity;
import com.tmsService.customer.Models.ResponseVo;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CustomerDao {
    ResponseEntity<ResponseVo> addCustomer(CustomerEntity customer);
    ResponseEntity<ResponseVo> getCustomers();
    ResponseEntity<ResponseVo> getCustomer(UUID idCustomer);
    ResponseEntity<ResponseVo> lockOrUnlockCustomer(UUID idCustomer,Boolean lock);
    ResponseEntity<ResponseVo> updateCustomer(CustomerEntity customer);


}
