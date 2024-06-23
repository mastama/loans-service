package com.mastama.loans.service;

import com.mastama.loans.dto.LoansDto;

public interface ILoansService {
    /**
     * @param mobileNumber - Mobile Number of the customer
     */
    void createLoan(String mobileNumber);

    /**
     * @param mobileNumber - Input Mobile Number of the customer
     * @return Loan details based on a given mobile number
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     * @ResponseBody loansDto - LoansDto Object
     * @return boolean indicating if the update of Loans details is successful or not
     */
    boolean updateLoan(LoansDto loansDto);

    /**
     * @param mobleNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteLoan(String mobleNumber);
}
