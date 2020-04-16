package br.com.viavarejo.backendchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Installment {

    private Integer installmentNumber;

    private BigDecimal value;

    private Double monthlyInterestRate = 0.0;

    public Installment(Integer installmentNumber, BigDecimal value) {
        this.installmentNumber = installmentNumber;
        this.value = value;
    }
}
