package com.tmsService.customer.Repositories;

import com.tmsService.customer.Entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    /**
     * Retrieves a list of customers that have not been deleted.
     *
     * @return  a list of CustomerEntity objects representing customers that have not been deleted
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.date_deleted IS NULL")
    List<CustomerEntity> findCustomersNotDeleted();
    /**
     * Finds customers by TVA number or reason sociale (case-insensitive).
     *
     * @param  param  the TVA number or reason sociale to search by
     * @return        a list of customer entities matching the search criteria
     */
    @Query("SELECT c FROM CustomerEntity c WHERE c.date_deleted IS NULL AND (LOWER(c.tva) LIKE LOWER(concat('%', :param, '%')) OR LOWER(c.reasonSociale) LIKE LOWER(concat('%', :param, '%')))")
    List<CustomerEntity> findCustomersByTvaOrReasonSocialeIgnoreCase(@Param("param") String param);

    @Query("SELECT c.idCustomer FROM CustomerEntity c WHERE c.date_deleted IS NULL AND (LOWER(c.reasonSociale) LIKE LOWER(concat('%', :resonSociale, '%')) )")
    List<UUID> findIdCustomersByReasonSocialeIgnoreCase(@Param("resonSociale") String resonSociale);





}
