package com.currency.tharun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CurrencyConverterService {

    // Load values from the environment (.env or system properties)
    @Value("${EXCHANGE_RATE_API_KEY}")
    private String apiKey;

    @Value("${EXCHANGE_RATE_BASE_URL}")
    private String baseUrl;

    public double convertCurrency(String from, String to, double amount) {
        // Create URL to fetch exchange rate
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + from)
                .queryParam("apikey", apiKey)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        // Get the exchange rate for the target currency
        double rate = response.getRates().get(to);

        // Return the converted amount
        return amount * rate;
    }

    // DTO to map the response from exchange rate API
    private static class ExchangeRateResponse {
        private String base;
        private Map<String, Double> rates;

        // Getters and setters
        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }
    }
}
