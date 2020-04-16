package br.com.viavarejo.backendchallenge.controller;

import br.com.viavarejo.backendchallenge.BackendChallengeIntegrationTest;
import br.com.viavarejo.backendchallenge.dto.CheckoutDTO;
import br.com.viavarejo.backendchallenge.dto.InstallmentDTO;
import br.com.viavarejo.backendchallenge.dto.PaymentTermDTO;
import br.com.viavarejo.backendchallenge.dto.ProductDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InstallmentControllerTest extends BackendChallengeIntegrationTest {

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        String selicResponse = "[{ \"data\": \"15/04/2020\", \"valor\": \"3.65\" }]";
        server.expect(requestTo(containsString("https://api.bcb.gov.br/dados/serie/bcdata.sgs.4189/dados?")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(selicResponse, MediaType.APPLICATION_JSON));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldCalculateInstallmentsInterestFree() throws Exception {
        ProductDTO productDTO = new ProductDTO(FAKER.number().randomNumber(), FAKER.funnyName().name(), BigDecimal.valueOf(100));
        PaymentTermDTO paymentTermDTO = new PaymentTermDTO(3, BigDecimal.ZERO);
        CheckoutDTO checkoutDTO = new CheckoutDTO(productDTO, paymentTermDTO);

        mockMvc.perform(post("/installments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].numeroParcela", is(1)))
                .andExpect(jsonPath("$.[0].valor", is(33.00)))
                .andExpect(jsonPath("$.[0].taxaJurosAoMes", is(0.00)))
                .andExpect(jsonPath("$.[1].numeroParcela", is(2)))
                .andExpect(jsonPath("$.[1].valor", is(33.00)))
                .andExpect(jsonPath("$.[1].taxaJurosAoMes", is(0.00)))
                .andExpect(jsonPath("$.[2].numeroParcela", is(3)))
                .andExpect(jsonPath("$.[2].valor", is(33.00)))
                .andExpect(jsonPath("$.[2].taxaJurosAoMes", is(0.00)));
    }

    @Test
    public void shouldCalculateInstallmentsInterest() throws Exception {
        ProductDTO productDTO = new ProductDTO(FAKER.number().randomNumber(), FAKER.funnyName().name(), BigDecimal.valueOf(100));
        PaymentTermDTO paymentTermDTO = new PaymentTermDTO(7, BigDecimal.ZERO);
        CheckoutDTO checkoutDTO = new CheckoutDTO(productDTO, paymentTermDTO);

        String contentAsString = mockMvc.perform(post("/installments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        InstallmentDTO[] installmentDTOS = objectMapper.readValue(contentAsString, InstallmentDTO[].class);

        assertEquals(7, installmentDTOS.length);

        Arrays.asList(installmentDTOS)
                .forEach(i -> {
                    assertEquals(BigDecimal.valueOf(18.36), i.getValue());
                    assertEquals(3.65, i.getMonthlyInterestRate());
                });
    }
}
