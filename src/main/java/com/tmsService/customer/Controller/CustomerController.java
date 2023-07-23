package com.tmsService.customer.Controller;

import com.tmsService.customer.Dao.CustomerDao;
import com.tmsService.customer.Entities.CustomerEntity;
import com.tmsService.customer.Models.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @GetMapping
    public ResponseEntity getAllCustomers() {
        return customerDao.getCustomers();
    }

    @GetMapping("/{idCustomer}")
    public ResponseEntity getCustomerById(@PathVariable UUID idCustomer) {
        return customerDao.getCustomer(idCustomer);
    }

    @PostMapping
    public ResponseEntity createCustomer(@RequestBody CustomerEntity customer) {
        return customerDao.addCustomer(customer);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseVo> updateCustomer(@RequestBody CustomerEntity customer) {
        return customerDao.updateCustomer(customer);
    }

    @PutMapping("/lock/{idCustomer}/{lock}")
    public ResponseEntity<ResponseVo> lockCustomer(@PathVariable UUID idCustomer, @PathVariable  Boolean lock) {
        return customerDao.lockOrUnlockCustomer(idCustomer, lock);
    }
    @PutMapping("/{idCustomer}")
    public  ResponseEntity deleteTransporter(@PathVariable UUID idCustomer){
        return customerDao.deleteCustomer(idCustomer) ;
    }



}
