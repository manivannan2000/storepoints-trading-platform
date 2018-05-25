package com.storepoints.etrade.api;

import static org.junit.Assert.*;
import java.text.DecimalFormat;

import org.junit.Test;

public class TestLong {

	@Test
	public void test() {
		 long  WILD_CARD_QUOTE_SLEEP_TIME_INIT=300000;
		 long wildCardQuoteSleepTime=300000;
		 
		 assertTrue(new Long(wildCardQuoteSleepTime).equals(new Long(WILD_CARD_QUOTE_SLEEP_TIME_INIT)));
		
		
	}

}
