package com.ex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.ex.Trade;

public final class StockItem {

	private String symbol;

	private boolean preferred;

	private long lastDivident;

	private long fixedDivident;

	private long parValue;

	private List<Trade> tradeList = new ArrayList<>();

	// computed values
	private StockItemComputed computed = new StockItemComputed();

	/**
	 * constructor for type = preferred
	 * 
	 * @param symbol
	 * @param preferred
	 * @param lastDivident
	 * @param fixedDivident
	 * @param parValue
	 */
	public StockItem(String symbol, boolean preferred, long lastDivident, long fixedDivident, long parValue) {
		super();
		this.symbol = symbol;
		this.preferred = preferred;
		this.lastDivident = lastDivident;
		this.fixedDivident = fixedDivident;
		this.parValue = parValue;
	}

	/**
	 * constructor for type = common
	 * 
	 * @param symbol
	 * @param lastDivident
	 * @param parValue
	 */
	public StockItem(String symbol, long lastDivident, long parValue) {
		super();
		this.symbol = symbol;
		this.preferred = false;
		this.lastDivident = lastDivident;
		this.fixedDivident = 0;
		this.parValue = parValue;
	}

	public String getSymbol() {
		return symbol;
	}

	public boolean isPreferred() {
		return preferred;
	}

	public long getLastDivident() {
		return lastDivident;
	}

	public long getFixedDivident() {
		return fixedDivident;
	}

	public long getParValue() {
		return parValue;
	}

	public void addTradeList(List<Trade> tradeList) {
		this.tradeList.addAll(tradeList);
	}

	public BigDecimal getStockPrice() {
		return computed.getStockPrice();
	}

	public void computeStockPrice() {

		BigDecimal stockPrice = BigDecimal.ZERO;

		// filter trades in last 15 minutes
		Stream<Trade> tradeSampleList = tradeList.parallelStream()
				.filter(trade -> trade.isTradeYoungerThan(StockData.RECORD_TIME_LIMIT));

		// compute sum value * quantity
		Stream<BigDecimal> tradeValueSampleList = tradeSampleList.map(Trade::computeTradeValue);
		BigDecimal totalValue = tradeValueSampleList.reduce(BigDecimal.ZERO, BigDecimal::add);

		if (totalValue.compareTo(BigDecimal.ZERO) == 0) {
			computed.setStockPrice(stockPrice);
			return;
		}

		// compute sum quantity
		Long totalQuantity = tradeList.parallelStream()
				.filter(trade -> trade.isTradeYoungerThan(StockData.RECORD_TIME_LIMIT)).mapToLong(Trade::getQuantity)
				.sum();

		// compute result
		stockPrice = totalValue.divide(BigDecimal.valueOf(totalQuantity));

		computed.setStockPrice(stockPrice);
	}

	private void computeDividend() {

		BigDecimal dividend;

		if (!preferred) {
			dividend = BigDecimal.valueOf(lastDivident);

		} else {
			// else common case
			BigDecimal bigDecParValue = BigDecimal.valueOf(parValue);

			dividend = BigDecimal.valueOf(fixedDivident).multiply(bigDecParValue).divide(BigDecimal.valueOf(100));
		}

		computed.setDividend(dividend);
	}

	public void computeCalculatedData() {
		computeStockPrice();
		computeDividend();
		computed.computeDividendYield();
		computed.computePE_Ratio();
	}

	public String toString() {
		String result = null;

		result = symbol + ": " + "Type= " + (preferred ? "Preferred" : "Common");

		result = result + " LastDivident= " + lastDivident;

		result = result + " FixedDivident= " + fixedDivident;

		result = result + " ParValue= " + parValue;

		result = result + "\nComputed:\n" + computed + "\n";

		return result;
	}

}
