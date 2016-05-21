package com.ex;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StockExchangeTable {

	private Map<String, StockItem> stocks = new HashMap<>();

	public void addStockItem(StockItem item) {
		stocks.put(item.getSymbol(), item);
	}

	public StockItem getStockItem(String symbol) {
		return stocks.get(symbol);
	}

	public void computeStockItemsCalculatedData() {
		stocks.values().parallelStream().forEach(StockItem::computeCalculatedData);
	}

	public BigDecimal computeGBCE_AllShareIndex() {
		BigDecimal result = BigDecimal.ZERO;

		if (stocks.size() == 0) {
			return result;
		}

		Stream<BigDecimal> stockItems = stocks.values().parallelStream().map(StockItem::getStockPrice)
				.filter(stockPrice -> stockPrice.compareTo(BigDecimal.ZERO) != 0);

		BigDecimal productStockPrices = stockItems.reduce(BigDecimal.ONE, BigDecimal::multiply);

		stockItems = stocks.values().parallelStream().map(StockItem::getStockPrice)
				.filter(stockPrice -> stockPrice.compareTo(BigDecimal.ZERO) != 0);

		Long count = stockItems.count();

		if (count == 0) {
			return result;
		}

		return BigDecimal.valueOf(Math.pow(productStockPrices.doubleValue(), 1.0 / count));
		// TODO delete me, return
		// MathUtils.sqrtNewtonRaphson(productStockPrices, BigDecimal.valueOf(
		// stockItems.count()), BigDecimal.TEN);

	}

	public String toString() {
		Stream<StockItem> orderedstockList = stocks.values().stream()
				.sorted(Comparator.comparing(StockItem::getSymbol));

		return orderedstockList.map(StockItem::toString).collect(Collectors.joining("\n"));

	}

}
