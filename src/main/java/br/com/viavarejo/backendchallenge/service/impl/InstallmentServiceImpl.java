package br.com.viavarejo.backendchallenge.service.impl;

import br.com.viavarejo.backendchallenge.domain.Checkout;
import br.com.viavarejo.backendchallenge.domain.Installment;
import br.com.viavarejo.backendchallenge.service.InstallmentService;
import br.com.viavarejo.backendchallenge.service.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InterestRateService interestRateService;

    @Value("${installment.min.interest-free}")
    private Integer minInterestFree;

    @Override
    public List<Installment> calculateInstallments(Checkout checkout) {
        BigDecimal balance = checkout.getValue().subtract(checkout.getInputValue());
        BigDecimal basicInstallmentValue = balance
                .divide(BigDecimal.valueOf(checkout.getInstallments()), ROUND_HALF_UP)
                .setScale(2, ROUND_HALF_UP);

        if(checkout.getInstallments() <= minInterestFree)
            return IntStream.rangeClosed(1, checkout.getInstallments())
                    .mapToObj(i -> new Installment(i, basicInstallmentValue))
                    .collect(toList());

        Double interestRateSelic = interestRateService.getInterestRateSelic(LocalDate.now());
        BigDecimal installmentValue = getInstallmentValue(balance, interestRateSelic, checkout.getInstallments());

        return IntStream.rangeClosed(1, checkout.getInstallments())
                .mapToObj(i -> new Installment(i, installmentValue, interestRateSelic))
                .collect(toList());
    }

    private BigDecimal getInstallmentValue(BigDecimal value, Double interestRatePerCent, Integer installmentsNumber) {
        double interestRate = interestRatePerCent / 100.00;
        return value.multiply(BigDecimal.ONE.add(BigDecimal.valueOf(interestRate)).pow(installmentsNumber))
                .divide(BigDecimal.valueOf(installmentsNumber), ROUND_HALF_UP)
                .setScale(2, ROUND_HALF_UP);
    }
}
