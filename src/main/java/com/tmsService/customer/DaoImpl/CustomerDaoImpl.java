package com.tmsService.customer.DaoImpl;

import com.tmsService.customer.CustomerUtils.CustomerConstants;
import com.tmsService.customer.Entities.CustomerEntity;
import com.tmsService.customer.Models.ResponseVo;
import com.tmsService.customer.Dao.CustomerDao;
import com.tmsService.customer.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CustomerDaoImpl implements CustomerDao {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public ResponseEntity<ResponseVo> addCustomer(CustomerEntity customer) {
        ResponseVo responseVo = new ResponseVo();
        customer.setIdUser(UUID.randomUUID());
        responseVo.setVo(customerRepository.save(customer));
        responseVo.setSuccessMsg(CustomerConstants.SUCCESS_CREATE_CUSTOMER);
        return ResponseEntity.ok(responseVo);
    }

    @Override
    public ResponseEntity<ResponseVo> getCustomers() {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setVo(customerRepository.findCustomersNotDeleted());
        return ResponseEntity.ok(responseVo);
    }

    @Override
    public ResponseEntity<ResponseVo> getCustomer(UUID idCustomer) {
        ResponseVo responseVo = new ResponseVo();
        CustomerEntity customer = customerRepository.findById(idCustomer).isPresent() ? customerRepository.findById(idCustomer).get() : null;
        if (customer == null) {
            responseVo.setWarningMsg(CustomerConstants.CUSTOMER_NOT_FOUND);
        }
        responseVo.setVo(customer);
        return ResponseEntity.ok(responseVo);
    }

    @Override
    public ResponseEntity<ResponseVo> lockOrUnlockCustomer(UUID idCustomer, Boolean lock) {
        ResponseVo responseVo = new ResponseVo();
        CustomerEntity customer = customerRepository.findById(idCustomer).isPresent() ? customerRepository.findById(idCustomer).get() : null;
        if (customer == null) {
            responseVo.setErrorsMsg(CustomerConstants.CUSTOMER_NOT_FOUND);
        } else if (lock != null) {
            if (Boolean.TRUE.equals(lock)) {
                customer.setBoo_isActif(Boolean.FALSE);
            } else {
                customer.setBoo_isActif(Boolean.TRUE);
            }
            customer.setDate_update(new Date());
            customer = customerRepository.save(customer);
        } else {
            responseVo.setErrorsMsg(CustomerConstants.NULL_LOCK_ACTION);
        }
        responseVo.setVo(customer);
        return ResponseEntity.ok(responseVo);
    }


    @Override
    public ResponseEntity<ResponseVo> updateCustomer(CustomerEntity customer) {
        ResponseVo responseVo = new ResponseVo();
        if (customer != null && customer.getIdCustomer() != null) {
            CustomerEntity existingCustomer = customerRepository.findById(customer.getIdCustomer()).isPresent() ? customerRepository.findById(customer.getIdCustomer()).get() : null;
            if (existingCustomer != null) {
                customer.setDate_update(new Date());
                customer.setIdUser(existingCustomer.getIdUser());
                existingCustomer = customerRepository.save(customer);
            } else {
                responseVo.setErrorsMsg(CustomerConstants.CUSTOMER_NOT_FOUND);
            }
            responseVo.setVo(existingCustomer);
        } else {
            responseVo.setErrorsMsg(CustomerConstants.NULL_CUSTOMER_OR_NOT_ID);
            responseVo.setVo(customer);
        }
        return ResponseEntity.ok(responseVo);
    }

    @Override
    public ResponseEntity<ResponseVo> deleteCustomer(UUID idTransporter) {
        ResponseVo responseVo=new ResponseVo();
        CustomerEntity transporter=customerRepository.findById(idTransporter).isPresent()?customerRepository.findById(idTransporter).get():null;
        if(transporter==null){
            responseVo.setWarningMsg(CustomerConstants.CUSTOMER_NOT_FOUND);
        }else{
            transporter.setDate_deleted(new Date());
            transporter.setDate_update(new Date());
            customerRepository.save(transporter);
            responseVo.setSuccessMsg(CustomerConstants.SUCCESS_DELETE_CUSTOMER);
        }
        return ResponseEntity.ok(responseVo);
    }
}
