package br.com.viavarejo.backendchallenge.service;

import java.time.LocalDate;

public interface InterestRateService {

    Double getInterestRateSelic(LocalDate localDate);
}
