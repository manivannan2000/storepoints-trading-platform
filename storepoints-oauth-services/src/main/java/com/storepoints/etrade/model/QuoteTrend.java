package com.storepoints.etrade.model;

import com.storepoints.ds.QuoteTrendQueue;

public class QuoteTrend {
	
    private QuoteTrendQueue quoteTrendQueue;
    
    private int trendCounter;
    
    public QuoteTrend(){}
    
    public QuoteTrend(double prevChgClosePrcn, int trendCounter){
    	this.quoteTrendQueue= new QuoteTrendQueue();
    	this.quoteTrendQueue.addToQueue(prevChgClosePrcn);
    	this.trendCounter=trendCounter;
    }

	public void setPrevChgClosePrcn(double prevChgClosePrcn) {
		this.quoteTrendQueue.addToQueue(prevChgClosePrcn);
	}

	public double getClimbCounter() {
		return this.quoteTrendQueue.getClimbCounter();
	}


	public double getFallCounter() {
		return this.quoteTrendQueue.getFallCounter();
	}
	
	public boolean isQueueSeventyPercentFilled(){
		return this.quoteTrendQueue.isQueueSeventyPercentFilled();
	}
	
	public boolean isQueueFiftyPercentFilled(){
		return this.quoteTrendQueue.isQueueSeventyPercentFilled();
	}


	public int getTrendCounter() {
		return trendCounter;
	}

	public void setTrendCounter(int trendCounter) {
		this.trendCounter = trendCounter;
	}

}
