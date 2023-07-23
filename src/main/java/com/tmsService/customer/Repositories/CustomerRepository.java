package com.tmsService.customer.Repositories;

import com.tmsService.customer.Entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    @Query("SELECT c FROM CustomerEntity c WHERE c.date_deleted IS NULL")
    List<CustomerEntity> findCustomersNotDeleted();
}
