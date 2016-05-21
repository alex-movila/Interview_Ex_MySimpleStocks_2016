package com.ex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

public class StockItemComputed {

	private BigDecimal stockPrice;
	private BigDecimal dividend;
	private BigDecimal dividendYield;
	private BigDecimal pe_ratio;

	public BigDecimal getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(BigDecimal stockPrice) {
		this.stockPrice = stockPrice;
	}

	public BigDecimal getDividend() {
		return dividend;
	}

	public void setDividend(BigDecimal dividend) {
		this.dividend = dividend;
	}

	public BigDecimal getDividendYield() {
		return dividendYield;
	}

	public void setDividendYield(BigDecimal dividendYield) {
		this.dividendYield = dividendYield;
	}

	public BigDecimal getPe_ratio() {
		return pe_ratio;
	}

	public void setPe_ratio(BigDecimal pe_ratio) {
		this.pe_ratio = pe_ratio;
	}

	public void computeDividendYield() {

		if (stockPrice.compareTo(BigDecimal.ZERO) == 0) {
			dividendYield = BigDecimal.ZERO;
			return;
		}

		dividendYield = dividend.divide(stockPrice, MathContext.DECIMAL32);
	}

	public void computePE_Ratio() {

		if (dividend.compareTo(BigDecimal.ZERO) == 0) {
			pe_ratio = BigDecimal.ZERO;
			return;
		}

		pe_ratio = stockPrice.divide(dividend, MathContext.DECIMAL32);
	}

	public String toString() {

		String result = null;

		DecimalFormat df = new DecimalFormat("##0.00");

		result = " StockPrice= " + df.format(stockPrice);

		result = result + " Dividend= " + df.format(dividend);

		result = result + " DividendYield= " + df.format(dividendYield);

		result = result + " P/E_Ratio= " + df.format(pe_ratio);

		return result;

	}

}
