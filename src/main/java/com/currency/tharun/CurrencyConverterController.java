package com.currency.tharun;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService currencyConverterService;

    // Endpoint to convert currency
    @GetMapping("/convert")
    public double convertCurrency(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        return currencyConverterService.convertCurrency(from, to, amount);
    }
}
