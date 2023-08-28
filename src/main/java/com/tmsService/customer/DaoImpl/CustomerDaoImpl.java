package com.tmsService.customer.DaoImpl;

import com.tmsService.customer.CustomerUtils.CustomerConstants;
import com.tmsService.customer.Entities.CustomerEntity;
import com.tmsService.customer.Models.ResponseVo;
import com.tmsService.customer.Dao.CustomerDao;
import com.tmsService.customer.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    /**
     * A method to lock or unlock a customer.
     *
     * @param  idCustomer  the ID of the customer
     * @param  lock        a boolean indicating whether to lock or unlock the customer
     * @return             a ResponseEntity containing a ResponseVo object
     */
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


    /**
     * Met à jour un client existant.
     *
     * @param  customer  le client à mettre à jour
     * @return           la réponse contenant les informations sur le succès de la mise à jour ou une erreur si le client n'a pas été trouvé ou si le client est null ou si l'ID du client est null
     */
    @Override
    public ResponseEntity<ResponseVo> updateCustomer(CustomerEntity customer) {
        ResponseVo responseVo = new ResponseVo();
        try {
            if (customer != null && customer.getIdCustomer() != null) {
                Optional<CustomerEntity> optionalExistingCustomer = customerRepository.findById(customer.getIdCustomer());
                if (optionalExistingCustomer.isPresent()) {
                    CustomerEntity existingCustomer = optionalExistingCustomer.get();
                    customer.setDate_update(new Date());
                    customer.setIdUser(existingCustomer.getIdUser());
                    existingCustomer = customerRepository.save(customer);
                    responseVo.setVo(existingCustomer);
                } else {
                    handleCustomerNotFoundError(responseVo);
                    return ResponseEntity.badRequest().body(responseVo);
                }
            } else {
                handleInvalidCustomerError(responseVo);
                return ResponseEntity.ok(responseVo);
            }
        } catch (DataAccessException e) {
            handleDataAccessError(responseVo);
            return ResponseEntity.ok(responseVo);
        } catch (Exception e) {
            handleInternalError(responseVo);
            return ResponseEntity.ok(responseVo);
        }
        return ResponseEntity.ok(responseVo);
    }



    /**
     * Supprime un client en fonction de son identifiant de transporteur.
     *
     * @param  idCustomer  l'identifiant du transporteur du client à supprimer
     * @return                la réponse contenant les informations sur le succès de la suppression ou un avertissement si le client n'a pas été trouvé
     */
    @Override
    public ResponseEntity<ResponseVo> deleteCustomer(UUID idCustomer) {
        ResponseVo responseVo = new ResponseVo();
        try {
            CustomerEntity transporter = customerRepository.findById(idCustomer).orElse(null);
            if (transporter == null) {
                handleCustomerNotFoundError(responseVo);
            } else {
                transporter.setDate_deleted(new Date());
                transporter.setDate_update(new Date());
                customerRepository.save(transporter);
                responseVo.setSuccessMsg(CustomerConstants.SUCCESS_DELETE_CUSTOMER);
            }
        } catch (DataAccessException e) {
            handleDataAccessError(responseVo);
        } catch (Exception e) {
            handleInternalError(responseVo);
        }
        return ResponseEntity.ok(responseVo);
    }

    /**
     * Retrieves customers based on the TVA number or the company name,
     * ignoring case sensitivity.
     *
     * @param  param  the TVA number or the company name to search for
     * @return        the response containing the list of matching customers
     */
    @Override
    public ResponseEntity<ResponseVo> findCustomersByTvaOrReasonSocialeIgnoreCase(String param) {
        ResponseVo responseVo = new ResponseVo();
        try {
            List<CustomerEntity> customers = customerRepository.findCustomersByTvaOrReasonSocialeIgnoreCase(param);
            responseVo.setVo(customers);
            responseVo.setSuccessMsg(CustomerConstants.SUCCESS_ACTION_CUSTOMER);
        } catch (IllegalArgumentException e) {
            handleInvalidParameterError(responseVo);
            return ResponseEntity.badRequest().body(responseVo);
        } catch (DataAccessException e) {
            handleDataAccessError(responseVo);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo);
        } catch (Exception e) {
            handleInternalError(responseVo);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo);
        }
        return ResponseEntity.ok(responseVo);
    }

    @Override
    public ResponseEntity<ResponseVo> findIdCustomersByReasonSocialeIgnoreCase(String resonSociale) {
        ResponseVo responseVo = new ResponseVo();
        try {
            List<UUID> idCustomers = customerRepository.findIdCustomersByReasonSocialeIgnoreCase(resonSociale);
            responseVo.setVo(idCustomers);
            responseVo.setSuccessMsg(CustomerConstants.SUCCESS_ACTION_CUSTOMER);
        } catch (IllegalArgumentException e) {
            handleInvalidParameterError(responseVo);
            return ResponseEntity.badRequest().body(responseVo);
        } catch (DataAccessException e) {
            handleDataAccessError(responseVo);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo);
        } catch (Exception e) {
            handleInternalError(responseVo);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo);
        }
        return ResponseEntity.ok(responseVo);
    }

    private void handleCustomerNotFoundError(ResponseVo responseVo) {
        responseVo.setErrorsMsg(CustomerConstants.CUSTOMER_NOT_FOUND);
        responseVo.setVo(null);
    }

    private void handleInvalidCustomerError(ResponseVo responseVo) {
        responseVo.setErrorsMsg(CustomerConstants.NULL_CUSTOMER_OR_NOT_ID);
        responseVo.setVo(null);
    }

    private void handleDataAccessError(ResponseVo responseVo) {
        responseVo.setErrorsMsg("Erreur d'accès aux données lors de la mise à jour du client");
    }

    private void handleInternalError(ResponseVo responseVo) {
        responseVo.setErrorsMsg("Erreur interne du serveur lors de la mise à jour du client");
    }
    private void handleInvalidParameterError(ResponseVo responseVo) {
        responseVo.setErrorsMsg("Paramètre invalide");
    }

}
