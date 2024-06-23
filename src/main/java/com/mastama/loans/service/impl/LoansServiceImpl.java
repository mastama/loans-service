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

import java.time.Year;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class LoansServiceImpl implements ILoansService {

    private final LoansRepository loansRepository;
    private static final String LOANS = "loans";
    private static final String MOBILE_NUMBER = "mobileNumber";

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
       long randomLoanNumber = Long.parseLong(generateLoanNumber("45678", "01"));

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
                () -> new ResourceNotFoundException(LOANS, MOBILE_NUMBER, mobileNumber)
        );

        // mapping data
        LoansDto loansDto = LoansMapper.mapToLoansDto(loans, new LoansDto());
        log.info("End Fetching loan: {}", mobileNumber);
        return loansDto;
    }

    @Override
    public boolean updateLoan(LoansDto loansDto) {
        log.info("Start Updating loan: {}", loansDto.getMobileNumber());
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException(LOANS, "loan number", loansDto.getLoanNumber())
        );

        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);

        log.info("End Updating loan: {}", loansDto.getMobileNumber());
        return true;
    }

    @Override
    public boolean deleteLoan(String mobleNumber) {
        log.info("Start Deleting loan: {}", mobleNumber);
        Loans loans = loansRepository.findByMobileNumber(mobleNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loans", "mobile number", mobleNumber)
        );
        loansRepository.deleteById(loans.getLoanId());

        log.info("End Deleting loan: {}", mobleNumber);
        return true;
    }

    private static String generateLoanNumber(String branchCode, String productCode) {
        Random random = new Random();
        // Pastikan kode cabang memiliki panjang 5 karakter
        if (branchCode.length() != 5) {
            throw new IllegalArgumentException("Branch code must be 5 digits long.");
        }

        // Pastikan kode produk memiliki panjang 2 karakter
        if (productCode.length() != 2) {
            throw new IllegalArgumentException("Product code must be 2 digits long.");
        }

        // Ambil dua digit terakhir dari tahun saat ini
        String year = String.valueOf(Year.now().getValue()).substring(2);

        // Hasilkan angka acak sepanjang 3 digit
        String randomNumber = String.format("%03d", random.nextInt(1000));

        // Gabungkan semua komponen
        return branchCode + year + productCode + randomNumber;
    }
}
