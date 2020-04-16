package br.com.viavarejo.backendchallenge.controller;

import br.com.viavarejo.backendchallenge.domain.Checkout;
import br.com.viavarejo.backendchallenge.domain.Installment;
import br.com.viavarejo.backendchallenge.dto.CheckoutDTO;
import br.com.viavarejo.backendchallenge.dto.InstallmentDTO;
import br.com.viavarejo.backendchallenge.service.InstallmentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/installments")
@RequiredArgsConstructor
@Api(tags = {"Prestações"}, value = "Compra de produto")
@Validated
public class InstallmentController {

    private final InstallmentService installmentService;

    private final ConversionService conversionService;

    @ApiOperation(value = "Calcula as parcelas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de parcelas"),
            @ApiResponse(code = 500, message = "Erro inesperado"),
    })
    @PostMapping
    @Validated
    public InstallmentDTO[] checkout(@RequestBody @NotNull @Valid @ApiParam(name = "requisicao", value = "Requisição", required = true) CheckoutDTO checkoutDTO) {
        Checkout checkout = conversionService.convert(checkoutDTO, Checkout.class);
        List<Installment> installments = installmentService.calculateInstallments(checkout);
        return conversionService.convert(installments.toArray(), InstallmentDTO[].class);
    }
}
