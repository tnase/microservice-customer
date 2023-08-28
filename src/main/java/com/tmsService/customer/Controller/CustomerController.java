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
    public ResponseEntity<ResponseVo> getAllCustomers() {
        return customerDao.getCustomers();
    }

    @GetMapping("/{idCustomer}")
    public ResponseEntity<ResponseVo> getCustomerById(@PathVariable UUID idCustomer) {
        return customerDao.getCustomer(idCustomer);
    }

    @PostMapping
    public ResponseEntity<ResponseVo> createCustomer(@RequestBody CustomerEntity customer) {
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
    public  ResponseEntity<ResponseVo> deleteTransporter(@PathVariable UUID idCustomer){
        return customerDao.deleteCustomer(idCustomer) ;
    }
    /**
     * Retrieves customers based on the provided key search.
     *
     * @param  keySearch  the key to search for customers
     * @return            the response containing the found customers
     */
    @GetMapping("/find/{keySearch}")
    public ResponseEntity<ResponseVo> findCustomersByTvaOrReasonSocialeIgnoreCase(@PathVariable String keySearch) {
        return customerDao.findCustomersByTvaOrReasonSocialeIgnoreCase(keySearch);
    }

    @GetMapping("/find/ids/{keySearch}")
    public ResponseEntity<ResponseVo> findIdCustomersByReasonSocialeIgnoreCase(@PathVariable String keySearch) {
        return customerDao.findIdCustomersByReasonSocialeIgnoreCase(keySearch);
    }


}
