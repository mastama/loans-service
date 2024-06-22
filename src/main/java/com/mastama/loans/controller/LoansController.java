package com.mastama.loans.controller;

import com.mastama.loans.constants.LoansConstants;
import com.mastama.loans.dto.LoansDto;
import com.mastama.loans.dto.ResponseDto;
import com.mastama.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Loans in BANKAPAYA",
        description = "CRUD REST APIs in BANKAPAYA to CREATE, UPDATE, FETCH AND DELETE loans details"
)
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Validated
public class LoansController {

    private final ILoansService iLoansService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new Loan inside BANKAPAYA"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@Valid @RequestParam
                                                      @Pattern(regexp = "^\\d{11,13}$", message = "MobileNumber must be numbers only and should be between 11 to 13 digits")
                                                      String mobileNumber) {
        log.info("Incoming Creating loan: {}", mobileNumber);
        iLoansService.createLoan(mobileNumber);
        log.info("Outgoing created loan: {}", mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201, mobileNumber));
    }

    @Operation(
            summary = "Fetch Loan REST API",
            description = "REST API to fetch Loan details inside BANKAPAYA"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<ResponseDto> fetchLoan(@Valid @RequestParam
                                                 @Pattern(regexp = "^\\d{11,13}$", message = "MobileNumber must be numbers only and should be between 11 to 13 digits")
                                                 String mobileNumber) {
        log.info("Incoming Fetching loan: {}", mobileNumber);
        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
        log.info("Outgoing fetched loan: {}", mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200, loansDto));
    }
}
