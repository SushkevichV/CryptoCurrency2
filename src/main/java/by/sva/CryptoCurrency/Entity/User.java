package by.sva.CryptoCurrency.Entity;

public class User {
	private String username;
	private String symbol;
	private Currency currency;
	
	public User() {
	}

	public User(String name, String symbol) {
		this.username = name;
		this.symbol = symbol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
