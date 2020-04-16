package br.com.viavarejo.backendchallenge.service.impl;

import br.com.viavarejo.backendchallenge.domain.Checkout;
import br.com.viavarejo.backendchallenge.domain.Installment;
import br.com.viavarejo.backendchallenge.service.InstallmentService;
import br.com.viavarejo.backendchallenge.service.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstallmentServiceImpl implements InstallmentService {

    private final InterestRateService interestRateService;

    @Value("${installment.min.interest-free}")
    private Integer minInterestFree;

    @Override
    public List<Installment> calculateInstallments(Checkout checkout) {
        if(checkout.getInstallments() <= minInterestFree)
            return checkout.getInstallmentInterestFree();

        Double interestRateSelic = interestRateService.getInterestRateSelic(LocalDate.now());

        return checkout.getInstallment(interestRateSelic);
    }
}
