package by.sva.CryptoCurrency;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/* простой REST-сервис просмотра котировок криптовалют

По условиям задачи в качестве кода валюты используется буквенный код валюты (symbol).
Список валют: [ {“id”:”90”,”symbol”:”BTC”}, {“id”:”80”,”symbol”:”ETH”}, {“id”:”48543”,”symbol”:”SOL”} ]
Раз в минуту актуальные цены для доступных криптовалют запрашиваются c внешнего источника CoinLore и сохраняются в базу данных.
Чтобы получить актуальные котировки по коду криптовалюты используйте open API Crypto API | https://www.coinlore.com/cryptocurrency-data-api#3
Меthod Ticker (Specific Coin): https://api.coinlore.net/api/ticker/?id=90 (BTC)

Функциональность:
Просмотр списка доступных криптовалют (REST-метод)
Просмотр актуальной цены для указаной криптовалюты (REST-метод - код валюты передается пользователем)
Записать в лог сообщение о изменении цены более чем на 1%. Для это пользователь регестрирует себя с помощью REST-метода notify и передает свое имя(username) 
 и код криптовалюты(symbol). В момент регистрации cохраняяется текущаю цена указаной криптовалюты с привязкой к пользоватлю. Как только актуальная цена для 
 указаной валюты поменялась более чем на 1%., в лог сервера выводится сообщение уровня WARN в котором указан: код валюты, имя пользователя
 и процент изменения цены с момента регистрации.
 * 
 *  есть подборка запросов в Postman
 */

@SpringBootApplication
@EnableAsync
public class CryptoCurrencyApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CryptoCurrencyApplication.class, args);
	}
	
	@Bean
	  public Executor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(2);
	    executor.setMaxPoolSize(2);
	    executor.setQueueCapacity(10);
	    executor.setThreadNamePrefix("CurrencyLoader-");
	    executor.initialize();
	    return executor;
	  }

}
