package com.tmsService.customer.Dao;

import com.tmsService.customer.Entities.CustomerEntity;
import com.tmsService.customer.Models.ResponseVo;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CustomerDao {
    /**
     * Adds a customer to the system.
     *
     * @param  customer  the customer entity to be added
     * @return           the response entity containing the response value
     */
    ResponseEntity<ResponseVo> addCustomer(CustomerEntity customer);
    /**
     * Retrieves a list of customers.
     *
     * @return         	Response entity containing a response value object
     */
    ResponseEntity<ResponseVo> getCustomers();
    /**
     * Retrieves a customer based on the provided customer ID.
     *
     * @param  idCustomer  the ID of the customer to retrieve
     * @return             the ResponseEntity containing the response data
     */
    ResponseEntity<ResponseVo> getCustomer(UUID idCustomer);
    /**
     * Generates a function comment for the given function body.
     *
     * @param  idCustomer  the ID of the customer
     * @param  lock        a boolean indicating whether to lock or unlock the customer
     * @return             the response entity containing the response value
     */
    ResponseEntity<ResponseVo> lockOrUnlockCustomer(UUID idCustomer,Boolean lock);
    /**
     * Updates a customer.
     *
     * @param  customer  the customer entity to be updated
     * @return           the response entity containing the updated response value
     */
    ResponseEntity<ResponseVo> updateCustomer(CustomerEntity customer);
    /**
     * Deletes a customer with the specified transporter ID.
     *
     * @param  idTransporter  the UUID of the transporter to be deleted
     * @return                the ResponseEntity containing the response for the delete operation
     */
    ResponseEntity<ResponseVo> deleteCustomer(UUID idTransporter);
    /**
     * A description of the entire Java function.
     *
     * @param  param   description of parameter
     * @return         description of return value
     */
    ResponseEntity<ResponseVo> findCustomersByTvaOrReasonSocialeIgnoreCase(String param);

    ResponseEntity<ResponseVo> findIdCustomersByReasonSocialeIgnoreCase(String resonSociale);


}
