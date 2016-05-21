package com.ex;

import java.math.BigDecimal;
import java.time.LocalTime;

public final class Trade {

	public Trade(long price, long quantity, boolean buy, LocalTime timestamp) {
		super();
		this.price = price;
		this.quantity = quantity;
		this.buy = buy;
		this.timestamp = timestamp;
	}

	// in pennies
	private long price;

	private long quantity;

	private boolean buy;

	private LocalTime timestamp;

	public long getPrice() {
		return price;
	}

	public long getQuantity() {
		return quantity;
	}

	public boolean isBuy() {
		return buy;
	}

	public LocalTime getTimestamp() {
		return timestamp;
	}

	public BigDecimal computeTradeValue() {
		BigDecimal result = BigDecimal.valueOf(price).multiply(BigDecimal.valueOf(quantity));

		// if sell then negate the result ? this could lead to negative stock
		// price so formula is no good
		// result = buy?result:result.negate();
		return result;
	}

	public boolean isTradeYoungerThan(long minutes) {
		return StockData.now.compareTo(timestamp.plusMinutes(minutes)) <= 0;
	}

}
