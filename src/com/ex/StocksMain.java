package com.ex;

public class StocksMain {

	public static void main(String[] args) {
		StockData stockData = new StockData();

		stockData.fillData();

		stockData.computeCalculatedData();

		stockData.printResults();

	}

}
