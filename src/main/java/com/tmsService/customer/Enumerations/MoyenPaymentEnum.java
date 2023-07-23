package com.tmsService.customer.Enumerations;

import com.tmsService.customer.CustomerUtils.CustomerConstants;

public enum MoyenPaymentEnum {
    CARTE_BANCAIRE(CustomerConstants.CARTE_BANCAIRE),
    VIREMENT(CustomerConstants.VIREMENT),
    CHEQUE(CustomerConstants.CHEQUE);
    private final String libelle;

    private MoyenPaymentEnum(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
