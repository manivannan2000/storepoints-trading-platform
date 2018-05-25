package com.storepoints.ds;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TrendQueue {
	
	protected int maxSize; // size of queue
	
	private Queue<Double> queue= new ConcurrentLinkedQueue<Double>();
	
	public TrendQueue(int maxSize){
		this.maxSize=maxSize;
	}
	
	public void addToQueue(Double percent){
		if(queue.size()>=maxSize){
			queue.remove();
		}
		queue.add(percent);
	}
	
	public boolean isQueueSeventyPercentFilled(){
		return queue.size()+(new Double(maxSize*0.3)).intValue() >= maxSize;
	}
	
	public boolean isQueueFiftyPercentFilled(){
		return queue.size()+(new Double(maxSize*0.5)).intValue() >= maxSize;
	}
	
	public double getClimbCounter(){
		double climbCounter=0.0D;
		Iterator<Double> iter=queue.iterator();
		

		Double percentPrev=null;

		while(iter.hasNext()){
			Double percent=iter.next();
			
			
			if(percentPrev!=null && percent>percentPrev){
				climbCounter=climbCounter+(percent-percentPrev);
			}
				
			percentPrev=percent;
		}
		
		return climbCounter;
	}
	
	public double getFallCounter(){
		
		double fallCounter=0.0D;
		
		Iterator<Double> iter=queue.iterator();
		
		Double percentPrev=null;
		while(iter.hasNext()){
			Double percent=iter.next();
			
			
			if(percentPrev!=null && percentPrev>percent){
				fallCounter=fallCounter+(percentPrev-percent);;
			}
			
			percentPrev=percent;
		}
		
		return fallCounter;
		
	}
}
