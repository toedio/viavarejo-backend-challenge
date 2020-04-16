package br.com.viavarejo.backendchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Checkout {

    private BigDecimal value;

    private BigDecimal inputValue;

    private Integer installments;

}
