package com.storepoints.etrade.dto;

public class GetQuoteParams {
	
	private String accessToken; 
	private String accessTokenSecret; 
	private String accountId; 
	private String quotesList; 
	private String detailFlag;  
	private int quantity; 
	private double chgClosePrcnLimit; 
	private boolean wildCardQuote;
	private boolean marketResearch;
	
	public GetQuoteParams() {}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getQuotesList() {
		return quotesList;
	}

	public void setQuotesList(String quotesList) {
		this.quotesList = quotesList;
	}

	public String getDetailFlag() {
		return detailFlag;
	}

	public void setDetailFlag(String detailFlag) {
		this.detailFlag = detailFlag;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isWildCardQuote() {
		return wildCardQuote;
	}

	public void setWildCardQuote(boolean wildCardQuote) {
		this.wildCardQuote = wildCardQuote;
	}

	public double getChgClosePrcnLimit() {
		return chgClosePrcnLimit;
	}

	public void setChgClosePrcnLimit(double chgClosePrcnLimit) {
		this.chgClosePrcnLimit = chgClosePrcnLimit;
	}

	public boolean isMarketResearch() {
		return marketResearch;
	}

	public void setMarketResearch(boolean marketResearch) {
		this.marketResearch = marketResearch;
	}

}
