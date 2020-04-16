package br.com.viavarejo.backendchallenge.converter;

import br.com.viavarejo.backendchallenge.domain.Checkout;
import br.com.viavarejo.backendchallenge.dto.CheckoutDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CheckoutDTOToCheckoutConverter implements Converter<CheckoutDTO, Checkout> {

    @Override
    public Checkout convert(CheckoutDTO checkoutDTO) {
        return new Checkout(checkoutDTO.getProduct().getPrice(),
                checkoutDTO.getPaymentTermDTO().getInputValue(),
                checkoutDTO.getPaymentTermDTO().getInstallments());
    }
}
