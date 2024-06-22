package com.mastama.loans.service.impl;

import com.mastama.loans.constants.LoansConstants;
import com.mastama.loans.dto.LoansDto;
import com.mastama.loans.entity.Loans;
import com.mastama.loans.exception.LoanAlreadyExistsException;
import com.mastama.loans.exception.ResourceNotFoundException;
import com.mastama.loans.mapper.LoansMapper;
import com.mastama.loans.repository.LoansRepository;
import com.mastama.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class LoansServiceImpl implements ILoansService {

    private final LoansRepository loansRepository;

    @Override
    public void createLoan(String mobileNumber) {
        log.info("Start Creating loan: {}", mobileNumber);
        // check if loans is existing
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobile number " + "- " + mobileNumber);
        }
        // save
        loansRepository.save(createNewLoan(mobileNumber));
        log.info("End Creating loan: {}", mobileNumber);
    }

    private Loans createNewLoan(String mobileNumber) {
        log.info("Start Creating new loan: {}", mobileNumber);
       Loans newLoan = new Loans();
       Random randomNumber = new Random();
       long randomLoanNumber = 10000000000L + randomNumber.nextInt(900000000);

       newLoan.setMobileNumber(mobileNumber);
       newLoan.setLoanNumber(Long.toString(randomLoanNumber));
       newLoan.setLoanType(LoansConstants.HOME_LOAN);
       newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
       newLoan.setAmountPaid(0);
       newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

       log.info("End Creating new loan: {}", mobileNumber);
       return newLoan;
    }

    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        log.info("Start Fetching loan: {}", mobileNumber);
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobile number", mobileNumber)
        );

        // mapping data
        LoansDto loansDto = LoansMapper.mapToLoansDto(loans, new LoansDto());
        log.info("End Fetching loan: {}", mobileNumber);
        return loansDto;
    }
}
