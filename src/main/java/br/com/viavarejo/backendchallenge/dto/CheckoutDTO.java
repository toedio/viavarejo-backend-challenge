package br.com.viavarejo.backendchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "requisicao")
public class CheckoutDTO {

    @ApiModelProperty(name = "produto", reference = "produto", required = true)
    @JsonProperty("produto")
    @Valid
    @NotNull
    private ProductDTO product;

    @ApiModelProperty(name = "condicaoPagamento", reference = "condicaoPagamento", required = true)
    @JsonProperty("condicaoPagamento")
    @Valid
    @NotNull
    private PaymentTermDTO paymentTermDTO;
}
