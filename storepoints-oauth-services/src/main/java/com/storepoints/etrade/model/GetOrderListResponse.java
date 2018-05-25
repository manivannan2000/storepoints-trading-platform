package com.storepoints.etrade.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "GetOrderListResponse", namespace="http://order.etws.etrade.com")
public class GetOrderListResponse {
	
	@JacksonXmlElementWrapper(localName = "orderListResponse", useWrapping = false)
	private OrderListResponse  orderListResponse;
	
	public GetOrderListResponse(){
		
	}


}
