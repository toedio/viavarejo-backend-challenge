package br.com.viavarejo.backendchallenge.service.impl;

import br.com.viavarejo.backendchallenge.domain.InterestRateSelicResponse;
import br.com.viavarejo.backendchallenge.service.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class InterestRateServiceImpl implements InterestRateService {

    private final RestTemplate restTemplate;

    @Value("${interestrate.selic.url}")
    private String urlSelic;

    @Value("${interestrate.selic.default}")
    private Double defaultSelic;

    @Override
    @Cacheable(value = "selic", key = "#localDate.toString()")
    public Double getInterestRateSelic(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDateParam = dateTimeFormatter.format(localDate.plusDays(-30));
        String endDateParam = dateTimeFormatter.format(localDate);
        URI uri = UriComponentsBuilder.fromHttpUrl(urlSelic)
                .queryParam("formato", "json")
                .queryParam("dataInicial", startDateParam)
                .queryParam("dataFinal", endDateParam)
                .build().toUri();

        try {
            ResponseEntity<InterestRateSelicResponse[]> responseEntity = restTemplate.getForEntity(uri, InterestRateSelicResponse[].class);

            if(Objects.isNull(responseEntity.getBody()))
                return defaultSelic;

            return Stream.of(responseEntity.getBody()).max(comparing(InterestRateSelicResponse::getDate))
                    .map(InterestRateSelicResponse::getValue).orElse(defaultSelic);
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            return defaultSelic;
        }

    }
}

