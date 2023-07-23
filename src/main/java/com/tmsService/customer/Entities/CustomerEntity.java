package com.tmsService.customer.Entities;


import com.tmsService.customer.Enumerations.ModePaymentEnum;
import com.tmsService.customer.Enumerations.MoyenPaymentEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "T_CUSTOMER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idCustomer;
    @Column(nullable = false)
    private String reasonSociale;
    private String Interlocuteur;
    private String codePostale;
    private String adresse;
    private String email;
    @Column(nullable = false)
    private String pays;
    private String phoneNumber;
    private String Fax;
    private String numeroSiret;
    private String delaisPayment;
    @Column(unique = true)
    private String tva;
    @Enumerated(EnumType.STRING)
    private MoyenPaymentEnum moyenPayment;
    @Enumerated(EnumType.STRING)
    private ModePaymentEnum modePayment;
    @Column(nullable = false)
    private Date date_create = new Date();
    private Date date_update;
    private Date date_deleted;
    @Column(nullable = false)
    private Boolean boo_isActif = Boolean.TRUE;
    @Column(nullable = false)
    private UUID idUser;

}
