package by.sva.CryptoCurrency.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.sva.CryptoCurrency.Entity.Currency;
import by.sva.CryptoCurrency.Entity.User;
import by.sva.CryptoCurrency.service.CurrencyService;

@RestController
public class CryptoController {
	
	private CurrencyService currencyService;
	
	public CryptoController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	
	// Получить курсы криптовалют из БД
	@GetMapping("")
	public ResponseEntity showALLCurrencies(@RequestParam(required = false) String symbol) {
		if(symbol == null) {
			List<Currency> currencies = currencyService.findAll();
			return ResponseEntity.ok(currencies);
		} else {
			Currency currency = currencyService.findFirstBySymbol(symbol);
			return ResponseEntity.ok(currency);
		}
	}
	
	// включить лог изменения курса выбранной валюты для указанного пользователя
	@PostMapping("/notify")
	public ResponseEntity notifyUser(@RequestBody User user) {
		
		user = currencyService.notifyUser(user);
		if(user != null) {
			return ResponseEntity.ok(user);
		}
		
		return ResponseEntity.badRequest().body("Currency not found");
	}
	
}
