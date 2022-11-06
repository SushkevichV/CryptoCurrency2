package by.sva.CryptoCurrency.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
	
	private CurrencyService currencyService;
	
	public AppRunner(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@Override
	public void run(String... args) throws Exception {
		currencyService.loadCurrency();
	}

}
