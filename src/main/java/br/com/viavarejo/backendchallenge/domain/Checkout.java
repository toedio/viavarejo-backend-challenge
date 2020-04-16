package br.com.viavarejo.backendchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class Checkout {

    private BigDecimal value;

    private BigDecimal inputValue;

    private Integer installments;

    public BigDecimal getBalance() {
        return value.subtract(inputValue);
    }

    public BigDecimal getBasicInstallmentValue() {
        return getBalance().divide(BigDecimal.valueOf(installments), ROUND_HALF_UP)
                .setScale(2, ROUND_HALF_UP);
    }

    public List<Installment> getInstallmentInterestFree() {
        return IntStream.rangeClosed(1, this.installments)
                .mapToObj(i -> new Installment(i, getBasicInstallmentValue()))
                .collect(toList());
    }

    public List<Installment> getInstallment(Double interestRateSelic) {
        double interestRate = interestRateSelic / 100.00;

        BigDecimal interest = BigDecimal.ONE.add(BigDecimal.valueOf(interestRate)).pow(this.installments);
        BigDecimal installmentValue = value.multiply(interest)
                .divide(BigDecimal.valueOf(this.installments), ROUND_HALF_UP)
                .setScale(2, ROUND_HALF_UP);

        return IntStream.rangeClosed(1, this.installments)
                .mapToObj(i -> new Installment(i, installmentValue, interestRateSelic))
                .collect(toList());
    }

}
