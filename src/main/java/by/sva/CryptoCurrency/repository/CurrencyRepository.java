package by.sva.CryptoCurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.sva.CryptoCurrency.Entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
	
	Currency findFirstBySymbol(String symbol);

}
