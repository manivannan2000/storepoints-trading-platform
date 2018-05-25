package com.storepoints.etrade.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "PlaceEquityOrder", namespace="http://order.etws.etrade.com")
public class PlaceEquityOrder {
	
	@JacksonXmlElementWrapper(localName = "EquityOrderRequest", useWrapping = false)
	private EquityOrderRequest  equityOrderRequest;
	
	public PlaceEquityOrder() {
		
	}
	

	@JacksonXmlProperty(localName="EquityOrderRequest")
	public EquityOrderRequest getEquityOrderRequest() {
		return equityOrderRequest;
	}

	public void setEquityOrderRequest(EquityOrderRequest equityOrderRequest) {
		this.equityOrderRequest = equityOrderRequest;
	}

}
