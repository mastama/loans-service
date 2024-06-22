package com.mastama.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Loans",
        description = "Schema to hold Loans Information"
)
@Data
public class LoansDto {

    /**
     * 	^: Menandakan awal string.
     * 	\d: Mencocokkan satu digit angka (0-9).
     * 	* / +: Menandakan nol atau lebih dari digit yang cocok sebelumnya.
     * 	{11,13}: Menandakan minimal 11 digit dan maksimal 13 digit.
     * 	$: Menandakan akhir string.
     */
    @Schema(description = "Mobile number of the customer", example = "081245678901")
    @Pattern(regexp = "^\\d{11,13}$", message = "MobileNumber must be numbers only and must be between 11 to 13 digits")
    private String mobileNumber;

    @NotEmpty(message = "Loan Number can not be a null or empty")
    @Schema(description = "Loans number of the customer", example = "548731272327")
    @Pattern(regexp = "^\\d{12}$", message = "Loan Number must be 12 digits")
    private String loanNumber;

    @NotEmpty(message = "Loan Type can not be a null or empty")
    @Schema(description = "Type of the loan", example = "Home Loan")
    private String loanType;

    @Positive(message = "Total loan amount should be greater than zero")
    @Schema(description = "Total loan amount", example = "1000000")
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    @Schema(description = "Total loan amount paid", example = "1000")
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    @Schema(description = "Total outstanding amount againts a loan", example = "990000")
    private int outstandingAmount;
}
