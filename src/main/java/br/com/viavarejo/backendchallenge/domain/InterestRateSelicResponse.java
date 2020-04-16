package br.com.viavarejo.backendchallenge.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestRateSelicResponse {

    @JsonProperty("data")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @JsonProperty("valor")
    private Double value;
}
