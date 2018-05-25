package com.storepoints.etrade.api;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.junit.Test;

public class LocalTimeTest {

	@Test
	public void test() {
		
		LocalTime thisTime= LocalTime.now();
		
		if(thisTime.getHour()==8)
			assertTrue(thisTime.getHour()==8);
		else
			assertTrue("Time is not 8 hours.",thisTime.getHour()!=8);
	}
	
	
	
//	@Test
	public void testOrderClosingMinutes(){
		String orderClosingMinutes="1";
		
		boolean orderClosingRequired=true;
		
		double percentIncreaseNow=6.73d;
		// failure test data
		//		double percentIncreaseNow=-0.09d;
		
		
		
		   LocalTime orderTime=LocalTime.now();
		   
		    try {
				Thread.sleep(61*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		   LocalTime orderTimePlusMins= orderTime.plusMinutes(Long.valueOf(orderClosingMinutes));
		   LocalTime orderTimePlusHour= orderTime.plusHours(1l);
		   
		   assertTrue(LocalTime.now().isAfter(orderTimePlusMins));
		   
		   
		   if((BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(6.0d))>0  && BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(10.0d))< 0  && LocalTime.now().isAfter(orderTimePlusMins)) ||
			  (BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(4.0d))>0  &&  BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(7.0d))< 0  && LocalTime.now().isAfter(orderTimePlusHour))){
			   assertTrue(orderClosingRequired);
		   } else {
			   fail("Test failed");
		   }
		   

	}

}
