package by.sva.CryptoCurrency.service;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import by.sva.CryptoCurrency.Entity.Currency;
import by.sva.CryptoCurrency.Entity.User;
import by.sva.CryptoCurrency.repository.CurrencyRepository;
import by.sva.CryptoCurrency.repository.CurrencyWebRepository;

@Service
public class CurrencyService {
	
	private CurrencyRepository currencyRepository;
	private CurrencyWebRepository currencyWebRepository;
	private static final Logger LOGGER = Logger.getLogger(CurrencyService.class);
	List<Currency> currencies;
	User user;

	public CurrencyService(CurrencyRepository currencyRepository, CurrencyWebRepository currencyWebRepository) {
		this.currencyRepository = currencyRepository;
		this.currencyWebRepository = currencyWebRepository;
	}

	public List<Currency> findAll() {
		return currencyRepository.findAll();
	}
	
	public Currency findFirstBySymbol(String symbol) {
		return currencyRepository.findFirstBySymbol(symbol);
	}

	public User notifyUser(User user) {
		user.setCurrency(currencyRepository.findFirstBySymbol(user.getSymbol()));
		// Если запрошенная валюта отсутствует в списке
		if(user.getCurrency() == null) {
			this.user = null;
		} else {
			this.user = user;
		}
		return this.user;
	}
	
	// проверить изменения курса
	public void check() {
		if(user != null) {
			// выбрать из списка валюту, которую выбрал пользователь
			Currency currency = currencies.get(currencies.indexOf(user.getCurrency()));
			
			if (Math.abs(currency.getPrice_usd()/user.getCurrency().getPrice_usd()*100-100) > 1) { // запись в лог при изменении курса более, чем на 1% с момента регистрации пользователя
				LOGGER.warn("Symbol: " + user.getCurrency().getSymbol() + ", User: " + user.getUsername() + ", Rate changed by "
						+ Math.abs(user.getCurrency().getPercent_change_1h()) + "% since registration");
			}
			if (Math.abs(currency.getPercent_change_1h()) > 1) { // запись в лог при изменении курса более, чем на 1% за последний час
				LOGGER.warn("Symbol: " + user.getCurrency().getSymbol() + ", User: " + user.getUsername() + ", The rate has changed by "
						+ Math.abs(user.getCurrency().getPercent_change_1h()) + "% in 1h");
			}
			if (Math.abs(currency.getPercent_change_24h()) > 1) { // запись в лог при изменении курса более, чем на 1% за 24 часа
				LOGGER.warn("Symbol: " + user.getCurrency().getSymbol() + ", User: " + user.getUsername() + ", The rate has changed by "
						+ Math.abs(user.getCurrency().getPercent_change_24h()) + "% in 24h");
			}
			if (Math.abs(currency.getPercent_change_7d()) > 1) { // запись в лог при изменении курса более, чем на 1% за день
				LOGGER.warn("Symbol: " + user.getCurrency().getSymbol() + ", User: " + user.getUsername() + ", The rate has changed by "
						+ Math.abs(user.getCurrency().getPercent_change_7d()) + "% in 7d");
			}
		}
	}
	
	// загрузка котировок с сервера
	@Async
	public void loadCurrency() throws InterruptedException {
		while(true) {
			currencies = currencyWebRepository.loadAllCurrencies();
			check();
			Thread.sleep(60000);
			//return CompletableFuture.completedFuture(currencies);
		}
	}

}
