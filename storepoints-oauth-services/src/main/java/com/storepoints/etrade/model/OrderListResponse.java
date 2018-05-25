package com.storepoints.etrade.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class OrderListResponse {

	@JacksonXmlElementWrapper(localName = "orderDetails", useWrapping = false)
	OrderDetail orderDetails = new OrderDetail();

	public OrderListResponse(){
		
	}
	

}
