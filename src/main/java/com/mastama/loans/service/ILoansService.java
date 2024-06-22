package com.mastama.loans.service;

import com.mastama.loans.dto.LoansDto;

public interface ILoansService {
    /**
     * @param mobileNumber - Mobile Number of the customer
     */
    void createLoan(String mobileNumber);

    /**
     * @param mobileNumber - Mobile Number of the customer
     * @return
     */
    LoansDto fetchLoan(String mobileNumber);
}
