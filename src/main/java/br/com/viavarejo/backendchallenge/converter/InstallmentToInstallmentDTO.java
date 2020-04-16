package br.com.viavarejo.backendchallenge.converter;

import br.com.viavarejo.backendchallenge.domain.Installment;
import br.com.viavarejo.backendchallenge.dto.InstallmentDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InstallmentToInstallmentDTO implements Converter<Installment, InstallmentDTO> {

    @Override
    public InstallmentDTO convert(Installment installment) {
        return new InstallmentDTO(installment.getInstallmentNumber(), installment.getValue(), installment.getMonthlyInterestRate());
    }
}