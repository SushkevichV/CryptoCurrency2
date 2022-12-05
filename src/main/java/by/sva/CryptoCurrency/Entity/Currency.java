package by.sva.CryptoCurrency.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown=true) // игнорировать JSON-атрибуты, которые отсутствуют в классе
public class Currency {
	@Id
	private Long id;
	private String symbol;
	private String name;
	private Double price_usd;
	private Double percent_change_1h;
	private Double percent_change_24h;
	private Double percent_change_7d;
	
	public Currency() {
	}

	public Currency(Long id, String symbol, String name, Double price_usd, Double percent_change_24h,
			Double percent_change_1h, Double percent_change_7d) {
		this.id = id;
		this.symbol = symbol;
		this.name = name;
		this.price_usd = price_usd;
		this.percent_change_1h = percent_change_1h;
		this.percent_change_24h = percent_change_24h;
		this.percent_change_7d = percent_change_7d;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice_usd() {
		return price_usd;
	}

	public void setPrice_usd(Double price_usd) {
		this.price_usd = price_usd;
	}
	
	public Double getPercent_change_1h() {
		return percent_change_1h;
	}

	public void setPercent_change_1h(Double percent_change_1h) {
		this.percent_change_1h = percent_change_1h;
	}

	public Double getPercent_change_24h() {
		return percent_change_24h;
	}

	public void setPercent_change_24h(Double percent_change_24h) {
		this.percent_change_24h = percent_change_24h;
	}

	public Double getPercent_change_7d() {
		return percent_change_7d;
	}

	public void setPercent_change_7d(Double percent_change_7d) {
		this.percent_change_7d = percent_change_7d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
