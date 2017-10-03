package com.storepoints.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetQuoteAPIController {
	
	
	private static double chgClosePrcn_counter=0.0;
	
	private static double bid_counter=632.5;
	
	
	private static String getGBTC_QuoteResponse(){
		return "<QuoteResponse>"
				+"<QuoteData>"
				+"  <all>"
			      +"<adjNonAdjFlag>false</adjNonAdjFlag>"
			      +"<annualDividend>0.0</annualDividend>"
			      +"<ask>631.0</ask>"
			      +"<askExchange>V</askExchange>"
			      +"<askSize>1</askSize>"
			      +"<askTime>08:22:29 EDT 09-18-2017</askTime>"
			      +"<bid>"+bid_counter+"</bid>"
			      +"<bidExchange>V</bidExchange>"
			      +"<bidSize>1</bidSize>"
			      +"<bidTime>08:22:29 EDT 09-18-2017</bidTime>"
			      +"<chgClose>0.0049999999999954525</chgClose>"
			      +"<chgClosePrcn>"+chgClosePrcn_counter+"</chgClosePrcn>"
			      +"<companyName>BITCOIN INVT TR</companyName>"
			      +"<daysToExpiration>0</daysToExpiration>"
			      +"<dirLast>D</dirLast>"
			      +"<dividend>0.0</dividend>"
			      +"<eps>-2.0</eps>"
			      +"<estEarnings>0.0</estEarnings>"
			      +"<exDivDate></exDivDate>"
			      +"<exchgLastTrade>NASDAQ BB Foreign</exchgLastTrade>"
			      +"<fsi> </fsi>"
			      +"<high>0.0</high>"
			      +"<high52>1064.95</high52>"
			      +"<highAsk>700.0</highAsk>"
			      +"<highBid>630.5</highBid>"
			      +"<lastTrade>630.01</lastTrade>"
			      +"<low>0.0</low>"
			      +"<low52>86.0</low52>"
			      +"<lowAsk>630.01</lowAsk>"
			      +"<lowBid>525.0</lowBid>"
			      +"<numTrades>0</numTrades>"
			      +"<open>0.0</open>"
			      +"<openInterest>0</openInterest>"
			      +"<optionStyle></optionStyle>"
			      +"<optionUnderlier></optionUnderlier>"
			      +"<prevClose>630.005</prevClose>"
			      +"<prevDayVolume>161301</prevDayVolume>"
			      +"<primaryExchange>u </primaryExchange>"
			      +"<symbolDesc>BITCOIN INVT TR</symbolDesc>"
			      +"<todayClose>0.0</todayClose>"
			      +"<totalVolume>0</totalVolume>"
			      +"<upc>0</upc>"
			      +"<volume10Day>122579</volume10Day>"
			    +"</all>"
			    +"<dateTime>16:26:23 EDT 09-18-2017</dateTime>"
			    +"<product>"
			    +"  <symbol>GBTC</symbol>"
			    +"  <type>EQ</type>"
			    +"  <exchange>u </exchange>"
			    +"</product>"
			  +"</QuoteData>"
			  + "</QuoteResponse>";
	}
	
	
    @RequestMapping("/market/rest/quote/GBTC")
    public String accountsList() {
    	
    	chgClosePrcn_counter=chgClosePrcn_counter+1.0;
    	
    	if(chgClosePrcn_counter>=6.0){
    		bid_counter= bid_counter+20.0;
    	}
    	
    	return getGBTC_QuoteResponse();
    }

}
