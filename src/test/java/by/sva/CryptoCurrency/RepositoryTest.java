package by.sva.CryptoCurrency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.sva.CryptoCurrency.Entity.Currency;
import by.sva.CryptoCurrency.Entity.User;
import by.sva.CryptoCurrency.repository.CurrencyRepository;
import by.sva.CryptoCurrency.repository.CurrencyWebRepository;
import by.sva.CryptoCurrency.service.CurrencyService;

@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	CurrencyRepository currencyRepository;
	
	@Autowired
	CurrencyWebRepository currencyWebRepository;
	
	@Autowired
	CurrencyService currencyService;
	
	@Test
	public void findAll() throws Exception {
		List<Currency> currencies = currencyRepository.findAll();
		assertThat(currencies).hasSize(3);
	}
	
	@Test
	public void findFirstBySymbol() throws Exception {
		Currency currency = currencyRepository.findFirstBySymbol("BTC");
		assertThat(currency != null);
		assertThat(currency.getId()).isEqualTo(90);
	}
	
	@Test
	public void loadAllCurrencies() throws Exception {
		List<Currency> currencies = currencyWebRepository.loadAllCurrencies();
		assertThat(currencies).hasSize(3);
	}
	
	@Test
	public void notifyUserWithValidCurrency() throws Exception {
		User user = new User("Sam", "ETH");
		user = currencyService.notifyUser(user);
		assertThat(user.getCurrency().getSymbol()).isEqualTo("ETH");
	}
	
	@Test
	public void notifyUserWithInvalidCurrency() throws Exception {
		User user = new User("Sam", "WWW");
		user = currencyService.notifyUser(user);
		assertThat(user).isEqualTo(null);
	}
	
	@Test
	public void percentValidation() throws Exception {
		Currency currency = Mockito.mock(Currency.class);
		Mockito.when(currency.getPrice_usd()).thenReturn(100.0);
		Double percentSinceRegistration = Math.round((currency.getPrice_usd()/102.15-1)*10000)/100.0;
		assertThat(percentSinceRegistration).isEqualTo(-2.10);
	}

}
