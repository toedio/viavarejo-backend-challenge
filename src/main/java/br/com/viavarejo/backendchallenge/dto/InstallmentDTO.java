package br.com.viavarejo.backendchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("listaDeParcelas")
public class InstallmentDTO {

    @ApiModelProperty(name = "numeroParcela", value = "Número da parcela", example = "1")
    @JsonProperty("numeroParcela")
    private Integer installmentNumber;

    @ApiModelProperty(name = "valor", value = "Valor da parcela", example = "9999.99")
    @JsonProperty("valor")
    private BigDecimal value;

    @ApiModelProperty(name = "taxaJurosAoMes", value = "Taxa de juros ao mês", example = "9999")
    @JsonProperty("taxaJurosAoMes")
    private Double monthlyInterestRate;
}
