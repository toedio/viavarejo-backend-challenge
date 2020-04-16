package br.com.viavarejo.backendchallenge.service.impl;

import br.com.viavarejo.backendchallenge.BackendChallengeUnitTest;
import br.com.viavarejo.backendchallenge.domain.InterestRateSelicResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class InterestRateServiceImplTest extends BackendChallengeUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private InterestRateServiceImpl interestRateService;

    @Mock
    private InterestRateSelicResponse interestRateSelicResponse;

    @BeforeAll
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(interestRateService, "defaultSelic", 2.0);
        ReflectionTestUtils.setField(interestRateService, "urlSelic", "https://api.bcb.gov.br/dados/serie/bcdata.sgs.4189/dados?formato=json");
    }

    @BeforeEach
    public void resetMock() {
        reset(restTemplate);
    }

    @Test
    public void shouldGetInterestRateSelic() {
        LocalDate localDate = LocalDate.of(2020, 4, 8);

        ArgumentCaptor<URI> uriArgumentCaptor = ArgumentCaptor.forClass(URI.class);

        given(interestRateSelicResponse.getDate())
                .willReturn(LocalDate.now());

        given(interestRateSelicResponse.getValue())
                .willReturn(3.0);

        given(restTemplate.getForEntity(uriArgumentCaptor.capture(), eq(InterestRateSelicResponse[].class)))
                .willReturn(ResponseEntity.ok(new InterestRateSelicResponse[]{interestRateSelicResponse}));

        Double interestRateSelic = interestRateService.getInterestRateSelic(localDate);
        assertEquals(3.0, interestRateSelic);

        verify(restTemplate, times(1))
                .getForEntity(any(URI.class), eq(InterestRateSelicResponse[].class));

        URI uri = uriArgumentCaptor.getValue();
        assertEquals("api.bcb.gov.br", uri.getHost());
        assertEquals("/dados/serie/bcdata.sgs.4189/dados", uri.getPath());
        assertEquals("formato=json&formato=json&dataInicial=09/03/2020&dataFinal=08/04/2020", uri.getQuery());
    }

    @Test
    public void shouldGetDefaultInterestRateSelicServerError() {
        LocalDate localDate = LocalDate.of(2020, 4, 3);

        ArgumentCaptor<URI> uriArgumentCaptor = ArgumentCaptor.forClass(URI.class);

        given(restTemplate.getForEntity(uriArgumentCaptor.capture(), eq(InterestRateSelicResponse[].class)))
                .willReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

        Double interestRateSelic = interestRateService.getInterestRateSelic(localDate);
        assertEquals(2.0, interestRateSelic);

        verify(restTemplate, times(1))
                .getForEntity(any(URI.class), eq(InterestRateSelicResponse[].class));

        URI uri = uriArgumentCaptor.getValue();
        assertEquals("api.bcb.gov.br", uri.getHost());
        assertEquals("/dados/serie/bcdata.sgs.4189/dados", uri.getPath());
        assertEquals("formato=json&formato=json&dataInicial=04/03/2020&dataFinal=03/04/2020", uri.getQuery());
    }

    @Test
    public void shouldGetDefaultInterestRateSelicBodyNull() {
        LocalDate localDate = LocalDate.of(2020, 5, 19);

        ArgumentCaptor<URI> uriArgumentCaptor = ArgumentCaptor.forClass(URI.class);

        given(restTemplate.getForEntity(uriArgumentCaptor.capture(), eq(InterestRateSelicResponse[].class)))
                .willReturn(ResponseEntity.noContent().build());

        Double interestRateSelic = interestRateService.getInterestRateSelic(localDate);
        assertEquals(2.0, interestRateSelic);

        verify(restTemplate, times(1))
                .getForEntity(any(URI.class), eq(InterestRateSelicResponse[].class));

        URI uri = uriArgumentCaptor.getValue();
        assertEquals("api.bcb.gov.br", uri.getHost());
        assertEquals("/dados/serie/bcdata.sgs.4189/dados", uri.getPath());
        assertEquals("formato=json&formato=json&dataInicial=19/04/2020&dataFinal=19/05/2020", uri.getQuery());
    }

}