package com.storepoints.etrade.model;

public class MarketResearchQuote implements Comparable<MarketResearchQuote> {
	
	
	private String quote;
	private double chgClosePrcn;
	
	public MarketResearchQuote(String quote, double chgClosePrcn){
		this.quote=quote;
		this.chgClosePrcn=chgClosePrcn;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public double getChgClosePrcn() {
		return chgClosePrcn;
	}

	public void setChgClosePrcn(double chgClosePrcn) {
		this.chgClosePrcn = chgClosePrcn;
	}

	@Override
	public int compareTo(MarketResearchQuote o) {
		 if(chgClosePrcn>o.chgClosePrcn){  
		        return 1;  
		    }else if(chgClosePrcn<o.chgClosePrcn){  
		        return -1;  
		    }else{  
		    	return 0;  
		    }  
	}

}
