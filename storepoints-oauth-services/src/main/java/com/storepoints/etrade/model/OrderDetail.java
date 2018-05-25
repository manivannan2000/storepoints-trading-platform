package com.storepoints.etrade.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class OrderDetail {
	
	@JacksonXmlElementWrapper(localName = "orderDetails", useWrapping = false)
	List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();
	
	public OrderDetail(){}
	


}
