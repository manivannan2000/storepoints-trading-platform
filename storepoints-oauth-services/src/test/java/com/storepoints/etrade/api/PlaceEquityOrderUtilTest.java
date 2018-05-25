package com.storepoints.etrade.api;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.storepoints.etrade.api.PlaceEquityOrderUtil;
import com.storepoints.etrade.model.EquityOrder;

import junit.framework.TestCase;

public class PlaceEquityOrderUtilTest extends TestCase {
	
	private EquityOrder equityOrder;

	
	
	@Before
	public void setUp(){
		
        equityOrder= new EquityOrder();
        equityOrder.setAccountId(123);
        equityOrder.setSymbol("AAPL");
        equityOrder.setOrderAction("BUY");
        equityOrder.setClientOrderId(""+(int) (Math.random() * 100000000));
        equityOrder.setPriceType("LIMIT");
        equityOrder.setLimitPrice(100);
        equityOrder.setQuantity(10);
        equityOrder.setMarketSession("EXTENDED");
        equityOrder.setOrderTerm("GOOD_UNTIL_CANCEL");
	}
	
	@Test
	public void testPlaceEquityOrderReqXML() throws JsonProcessingException{
		PlaceEquityOrderUtil placeEquityOrderUtil= new PlaceEquityOrderUtil("abc","xyz", equityOrder);
		System.out.println("Request XML :"+placeEquityOrderUtil.getRequestXML());
		assertTrue(placeEquityOrderUtil.getRequestXML()!=null);
	}	
}
