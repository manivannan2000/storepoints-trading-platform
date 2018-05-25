package com.storepoints.etrade.model;

import com.storepoints.ds.ProfitTrendQueue;

public class ProfitTrend {
	
    private ProfitTrendQueue profitTrendQueue;
    
    public ProfitTrend(){}
    
    public ProfitTrend(double percent){
    	this.profitTrendQueue= new ProfitTrendQueue();
    	this.profitTrendQueue.addToQueue(percent);
    }

	public void setPrevProfitPrcn(double prevPercent) {
		this.profitTrendQueue.addToQueue(prevPercent);
	}

	public double getClimbCounter() {
		return this.profitTrendQueue.getClimbCounter();
	}


	public double getFallCounter() {
		return this.profitTrendQueue.getFallCounter();
	}

}
