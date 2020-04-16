package br.com.viavarejo.backendchallenge.service.impl;

import br.com.viavarejo.backendchallenge.BackendChallengeUnitTest;
import br.com.viavarejo.backendchallenge.domain.Checkout;
import br.com.viavarejo.backendchallenge.domain.Installment;
import br.com.viavarejo.backendchallenge.service.InterestRateService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

class InstallmentServiceImplTest extends BackendChallengeUnitTest {

    @Mock
    private InterestRateService interestRateService;

    @InjectMocks
    private InstallmentServiceImpl installmentService;

    @BeforeAll
    public void setup() {
        initMocks(this);
        ReflectionTestUtils.setField(installmentService, "minInterestFree", 3);
    }

    @Test
    public void shouldCalculateInstallmentsInterestFree() {
        Checkout checkout = new Checkout(BigDecimal.valueOf(84.28), BigDecimal.ZERO, 2);
        List<Installment> installments = installmentService.calculateInstallments(checkout);
        assertEquals(2, installments.size());

        assertEquals(BigDecimal.valueOf(42.14), installments.get(0).getValue());
        assertEquals(1, installments.get(0).getInstallmentNumber());
        assertEquals(0.0, installments.get(0).getMonthlyInterestRate());

        assertEquals(BigDecimal.valueOf(42.14), installments.get(1).getValue());
        assertEquals(2, installments.get(1).getInstallmentNumber());
        assertEquals(0.0, installments.get(1).getMonthlyInterestRate());
    }

    @Test
    public void shouldCalculateInstallmentsWithInterest() {
        given(interestRateService.getInterestRateSelic(eq(LocalDate.now())))
                .willReturn(3.0);

        Checkout checkout = new Checkout(BigDecimal.valueOf(200.00), BigDecimal.ZERO, 10);
        List<Installment> installments = installmentService.calculateInstallments(checkout);
        assertEquals(10, installments.size());

        AtomicInteger atomicInteger = new AtomicInteger(1);
        installments.forEach(i -> {
            assertEquals(BigDecimal.valueOf(26.88), i.getValue());
            assertEquals(atomicInteger.getAndAdd(1), i.getInstallmentNumber());
            assertEquals(3.0, i.getMonthlyInterestRate());
        });
    }
}