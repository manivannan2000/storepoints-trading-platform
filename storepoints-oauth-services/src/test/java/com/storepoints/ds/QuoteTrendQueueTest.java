package com.storepoints.ds;

import static org.junit.Assert.*;

import org.junit.Test;

public class QuoteTrendQueueTest {

	@Test
	public void test() {
		
		QuoteTrendQueue quoteQueue= new QuoteTrendQueue();
		
		quoteQueue.addToQueue(12d);
		quoteQueue.addToQueue(14d);
		quoteQueue.addToQueue(9d);
		quoteQueue.addToQueue(10d);
		quoteQueue.addToQueue(11d);
		
		assertTrue(quoteQueue.getClimbCounter()==4.0D);
		
		
		
	}
	
	@Test
	public void testFallCounter() {
		
		QuoteTrendQueue quoteQueue= new QuoteTrendQueue();
		quoteQueue.addToQueue(17d);
		quoteQueue.addToQueue(16d);
		quoteQueue.addToQueue(15d);
		quoteQueue.addToQueue(14d);
		quoteQueue.addToQueue(13d);
		quoteQueue.addToQueue(12d);
		quoteQueue.addToQueue(11d);
		quoteQueue.addToQueue(11d);
		quoteQueue.addToQueue(12d);
		quoteQueue.addToQueue(8d);
		
		assertTrue(quoteQueue.getFallCounter()==10.0D);
		
		
		
	}
	

}
