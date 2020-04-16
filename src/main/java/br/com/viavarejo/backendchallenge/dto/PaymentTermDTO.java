package br.com.viavarejo.backendchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "condicaoPagamento")
public class PaymentTermDTO {

    @ApiModelProperty(value = "Quantidade de parcelas", name = "qtdeParcelas", allowableValues = "range[1, infinity]", required = true, example = "999")
    @JsonProperty("qtdeParcelas")
    @NotNull
    @Min(value = 1)
    private Integer installments;

    @ApiModelProperty(value = "Valor de entrada", name = "valorEntrada", allowableValues = "range[0, infinity]", required = true, example = "9999.99")
    @JsonProperty("valorEntrada")
    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal inputValue;
}
