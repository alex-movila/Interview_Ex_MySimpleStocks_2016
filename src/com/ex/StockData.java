package com.ex;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StockData {

	private StockExchangeTable stockExchangeTable = new StockExchangeTable();

	public static LocalTime now = LocalTime.now();

	public static final int RECORD_TIME_LIMIT = 15;

	private void addStockItem(StockItem item) {
		stockExchangeTable.addStockItem(item);
	}

	private void fillStockExchangeTable() {
		StockItem item = new StockItem("TEA", 0, 100);
		addStockItem(item);

		item = new StockItem("POP", 8, 100);
		addStockItem(item);

		item = new StockItem("ALE", 23, 60);
		addStockItem(item);

		item = new StockItem("GIN", true, 8, 2, 100);
		addStockItem(item);

		item = new StockItem("JOE", 13, 250);
		addStockItem(item);

	}

	private void fillTradesForSameSymbol(String symbol, List<Trade> tradeList) {
		StockItem stockItem = stockExchangeTable.getStockItem(symbol);

		if (stockItem != null) {
			stockItem.addTradeList(tradeList);
		}

	}

	private void fillTrade(List<Trade> tradeList, long price, long quantity, boolean buy, int minutes) {
		Trade trade = new Trade(price, quantity, buy, now.plusMinutes(minutes));
		tradeList.add(trade);
	}

	private void fillTrades() {

		List<Trade> tradeList = new ArrayList<Trade>();

		// long price, long quantity, boolean buy, minutes ago
		fillTrade(tradeList, 2, 2, true, -3);
		fillTrade(tradeList, 2, 1, true, -5);
		fillTrade(tradeList, 1, 1, true, -20);

		fillTradesForSameSymbol("ALE", tradeList);
		tradeList.clear();

		fillTrade(tradeList, 2, 2, true, -3);
		fillTrade(tradeList, 2, 1, true, -5);
		fillTrade(tradeList, 1, 1, true, -20);

		fillTradesForSameSymbol("TEA", tradeList);
		tradeList.clear();

		fillTrade(tradeList, 1, 1, true, -20);
		fillTrade(tradeList, 1, 1, true, -16);

		fillTradesForSameSymbol("POP", tradeList);
		tradeList.clear();

		fillTrade(tradeList, 3, 1, true, -3);
		fillTrade(tradeList, 2, 1, true, -5);
		fillTrade(tradeList, 1, 1, true, -20);

		fillTradesForSameSymbol("GIN", tradeList);
		tradeList.clear();

	}

	public void fillData() {
		fillStockExchangeTable();
		fillTrades();
	}

	public void computeCalculatedData() {
		stockExchangeTable.computeStockItemsCalculatedData();
	}

	public void printResults() {
		System.out.println(stockExchangeTable);

		System.out.println("GBCE_AllShareIndex= " + stockExchangeTable.computeGBCE_AllShareIndex());
	}

}
