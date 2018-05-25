package com.storepoints.etrade.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class EquityOrderRequest {
	
	@JacksonXmlProperty(localName = "accountId")
	private int accountId;
	
	@JacksonXmlProperty(localName = "clientOrderId")
	private String clientOrderId;
	
	@JacksonXmlProperty(localName = "limitPrice")
	private double limitPrice;
	
	@JacksonXmlProperty(localName = "previewId")
	private String previewId;
	
	@JacksonXmlProperty(localName = "stopPrice")
	private String stopPrice;
	
	@JacksonXmlProperty(localName = "allOrNone")
	private String allOrNone;
	
	@JacksonXmlProperty(localName = "quantity")
	private int quantity;
	
	@JacksonXmlProperty(localName = "reserveOrder")
	private String reserveOrder;
	
	@JacksonXmlProperty(localName = "reserveQuantity")
	private String reserveQuantity;

	@JacksonXmlProperty(localName = "symbol")
	private String symbol;
	
	@JacksonXmlProperty(localName = "orderAction")
	private String orderAction;
	
	@JacksonXmlProperty(localName = "priceType")
	private String priceType;
	
	@JacksonXmlProperty(localName = "routingDestination")
	private String routingDestination;
	
	
	@JacksonXmlProperty(localName = "marketSession")
	private String marketSession;
	
	@JacksonXmlProperty(localName = "orderTerm")
	private String orderTerm;
	
	
	public EquityOrderRequest(){
		
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
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

	public String getPreviewId() {
		return previewId;
	}

	public void setPreviewId(String previewId) {
		this.previewId = previewId;
	}

	public String getClientOrderId() {
		return clientOrderId;
	}

	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public String getStopPrice() {
		return stopPrice;
	}

	public void setStopPrice(String stopPrice) {
		this.stopPrice = stopPrice;
	}

	public String isAllOrNone() {
		return allOrNone;
	}

	public void setAllOrNone(String allOrNone) {
		this.allOrNone = allOrNone;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String isReserveOrder() {
		return reserveOrder;
	}

	public void setReserveOrder(String reserveOrder) {
		this.reserveOrder = reserveOrder;
	}

	public String getReserveQuantity() {
		return reserveQuantity;
	}

	public void setReserveQuantity(String reserveQuantity) {
		this.reserveQuantity = reserveQuantity;
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

	public String getRoutingDestination() {
		return routingDestination;
	}

	public void setRoutingDestination(String routingDestination) {
		this.routingDestination = routingDestination;
	}

}
