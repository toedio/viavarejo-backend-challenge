package br.com.viavarejo.backendchallenge.service;

import br.com.viavarejo.backendchallenge.domain.Checkout;
import br.com.viavarejo.backendchallenge.domain.Installment;

import java.util.List;

public interface InstallmentService {

    List<Installment> calculateInstallments(Checkout checkout);
}
