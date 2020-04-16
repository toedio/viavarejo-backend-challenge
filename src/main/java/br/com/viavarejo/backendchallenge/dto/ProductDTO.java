package br.com.viavarejo.backendchallenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "produto")
public class ProductDTO {

    @ApiModelProperty(value = "Código do produto",name = "codigo", required = true, example = "123")
    @JsonProperty("codigo")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "Nome do produto",name = "nome", required = true, example = "Nome do Produto")
    @JsonProperty("nome")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Valor do produto",name = "valor", required = true, allowableValues = "range[0.01, infinity]", example = "9999")
    @JsonProperty("valor")
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;
}
