package com.storepoints.etrade.model;

import java.time.LocalTime;
import java.util.Date;

public class EquityOrder {
	
	private int accountId;
	private String clientOrderId;
	private double limitPrice; 
	private int quantity;
	private String symbol;
	private String orderAction;
	private String priceType;
	private String marketSession;
	private String orderTerm;
	
	// Response Element
    private int orderNum;
    private LocalTime orderTime;
    private boolean orderAlreadyClosed;
    
    private int orderClosedCounter;
	private double estimatedMarketPrice; 
    
    
	
	
	public EquityOrder(){
	}
	
	public EquityOrder(int accountId, String clientOrderId, double limitPrice, int quantity){
		this.accountId=accountId;
		this.clientOrderId=clientOrderId;
		this.limitPrice=limitPrice;
		this.quantity=quantity;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getClientOrderId() {
		return clientOrderId;
	}

	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getOrderAction() {
		return orderAction;
	}

	public void setOrderAction(String orderAction) {
		this.orderAction = orderAction;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getMarketSession() {
		return marketSession;
	}

	public void setMarketSession(String marketSession) {
		this.marketSession = marketSession;
	}

	public String getOrderTerm() {
		return orderTerm;
	}

	public void setOrderTerm(String orderTerm) {
		this.orderTerm = orderTerm;
	}
	
	
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}


	public LocalTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalTime orderTime) {
		this.orderTime = orderTime;
	}

	public boolean isOrderAlreadyClosed() {
		return orderAlreadyClosed;
	}

	public int getOrderClosedCounter() {
		return orderClosedCounter;
	}

	public void setOrderClosedCounter(int orderClosedCounter) {
		this.orderClosedCounter = orderClosedCounter;
	}

	public void setOrderAlreadyClosed(boolean orderAlreadyClosed) {
		this.orderAlreadyClosed = orderAlreadyClosed;
	}

	public double getEstimatedMarketPrice() {
		return estimatedMarketPrice;
	}

	public void setEstimatedMarketPrice(double estimatedMarketPrice) {
		this.estimatedMarketPrice = estimatedMarketPrice;
	}

	public String toString(){
		
		return symbol+" "+quantity+ " "+orderAction+" "+priceType+" "+marketSession+" "+orderTerm;
	}

}
