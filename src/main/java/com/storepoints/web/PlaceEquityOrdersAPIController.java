package com.storepoints.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaceEquityOrdersAPIController {
	
	
	private static int orderNum_counter=1;
	
	private static int counter=1;
	
	
	
	private static String getGBTC_OrderResponse(){
		return "<PlaceEquityOrderResponse>"
				+"<equityOrderResponse>"
				+"<accountId>83405188</accountId>"
				+"<allOrNone>false</allOrNone>"
				+"<estimatedCommission>7.99</estimatedCommission>"
				+"<estimatedTotalAmount>19.99</estimatedTotalAmount>"
				+"<messageList>"
				+"<message>"
				+"<msgDesc>"
				+" Your order was successfully entered during market hours."
				+"</msgDesc>"
				+"<msgCode>1026</msgCode>"
				+"</message>"
				+"</messageList>"
				+"<orderNum>"+orderNum_counter+"</orderNum>"
				+"<orderTime>1269423948429</orderTime>"
				+"<quantity>4</quantity>"
				+"<reserveOrder>false</reserveOrder>"
				+"<reserveQuantity>0</reserveQuantity>"
				+"<orderTerm>GOOD_FOR_DAY</orderTerm>"
				+"<limitPrice>3</limitPrice>"
				+"<stopPrice>0</stopPrice>"
				+"<symbolDesc></symbolDesc>"
				+"<symbol>GBTC</symbol>"
				+"<orderAction>BUY</orderAction>"
				+"<priceType>LIMIT</priceType>"
				+"</equityOrderResponse>"
				+"</PlaceEquityOrderResponse>";
	}
	
	
	private static String getErrorMessage_1516(){
		return "<Error><ErrorCode>1516</ErrorCode><ErrorMessage>Your buy order must execute before we can accept your sell order.</ErrorMessage></Error>";
	}
	
	
	
    @RequestMapping(value = "/order/rest/placeequityorder", method = RequestMethod.POST)
    public String accountsList() {
    	
    	orderNum_counter=(int)Math.random()*1000;
    	
    	if(counter%2!=0){
    		counter++;
    		return getGBTC_OrderResponse();	
    	} else {
    		return getErrorMessage_1516();	
    	}
    }

}
