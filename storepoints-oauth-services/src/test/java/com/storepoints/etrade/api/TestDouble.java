package com.storepoints.etrade.api;

import static org.junit.Assert.*;
import java.text.DecimalFormat;

import org.junit.Test;

public class TestDouble {

	@Test
	public void test() {


		DecimalFormat df2 = new DecimalFormat(".##");

		double input = 32.123456;
		
		String result=df2.format(input);
		
		double resultD=Double.parseDouble(result);
		
		System.out.println("ResultD:"+resultD);

		assertTrue(result.length()==5);
		
		
		
	}

}
