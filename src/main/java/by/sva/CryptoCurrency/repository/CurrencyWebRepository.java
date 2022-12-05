package by.sva.CryptoCurrency.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import by.sva.CryptoCurrency.Entity.Currency;

@Component
public class CurrencyWebRepository {
	private final CurrencyRepository currencyRepository;
	private final RestTemplate restTemplate;
	private List<Currency> currencies;

	public CurrencyWebRepository(CurrencyRepository currencyRepository, RestTemplateBuilder restTemplateBuilder) {
		this.currencyRepository = currencyRepository;
		this.restTemplate = restTemplateBuilder.build();
		currencies = currencyRepository.findAll();
	}
	
	// не используется
	// получить котировки валют и обновить БД
	// не самый оптимальный способ, т.к. на каждую валюту отправляется отдельный запрос
	public List<Currency> loadCurrencies() {
		for (Currency currency : currencies) {
			// получить id элемента и подставить его в URL
			String url = String.format("https://api.coinlore.net/api/ticker/?id=%s", currency.getId());
			// URL возвращает массив объектов, поэтому даже при запросе одного объекта принимать нужно массив
			currency = restTemplate.getForObject(url, Currency[].class)[0]; // взять из массива 0й элемент
		    currencyRepository.save(currency);
		}
		System.out.println("Currencies updated");
		return currencies;
	}
	
	// получить котировки валют и обновить БД
	// отправляется один общий запрос на все валюты
	public List<Currency> loadAllCurrencies() {
		// получить id элементов и подставить их в URL через разделитель
		String postfix = currencies.stream().map(id -> id.getId().toString()).collect(Collectors.joining(","));
		String url = String.format("https://api.coinlore.net/api/ticker/?id=%s", postfix);
		// URL возвращает массив объектов
		currencies = Arrays.asList(restTemplate.getForObject(url, Currency[].class)); // преобразовать массив в коллекцию
	    currencyRepository.saveAll(currencies);
		
	    //System.out.println("Currencies updated");
		return currencies;
	}

}
