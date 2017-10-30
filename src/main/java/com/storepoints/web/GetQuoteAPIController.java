package com.storepoints.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetQuoteAPIController {
	
	
	private static double chgClosePrcn_counter_BOLD=0.0;
	
	private static double chgClosePrcn_counter_GBTC=0.0;
	
	
	private static double bid_counter=632.5;
	private static double ask_counter=633.5;
	
	private static int counter=0;
	
	
	private static String getBOLD_QuoteResponse(){
		return "<QuoteResponse>"
				+"<QuoteData>"
				+"  <all>"
			      +"<adjNonAdjFlag>false</adjNonAdjFlag>"
			      +"<annualDividend>0.0</annualDividend>"
			      +"<ask>"+ask_counter+"</ask>"
			      +"<askExchange>V</askExchange>"
			      +"<askSize>1</askSize>"
			      +"<askTime>08:22:29 EDT 09-18-2017</askTime>"
			      +"<bid>"+bid_counter+"</bid>"
			      +"<bidExchange>V</bidExchange>"
			      +"<bidSize>1</bidSize>"
			      +"<bidTime>08:22:29 EDT 09-18-2017</bidTime>"
			      +"<chgClose>5.0</chgClose>"
			      +"<chgClosePrcn>"+chgClosePrcn_counter_BOLD+"</chgClosePrcn>"
			      +"<companyName>Audentes Therapeutics Inc</companyName>"
			      +"<daysToExpiration>0</daysToExpiration>"
			      +"<dirLast>D</dirLast>"
			      +"<dividend>0.0</dividend>"
			      +"<eps>-2.0</eps>"
			      +"<estEarnings>0.0</estEarnings>"
			      +"<exDivDate></exDivDate>"
			      +"<exchgLastTrade>NASDAQ BB Foreign</exchgLastTrade>"
			      +"<fsi> </fsi>"
			      +"<high>0.0</high>"
			      +"<high52>33.43</high52>"
			      +"<highAsk>24.98</highAsk>"
			      +"<highBid>33.43</highBid>"
			      +"<lastTrade>33.43</lastTrade>"
			      +"<low>0.0</low>"
			      +"<low52>13.13</low52>"
			      +"<lowAsk>25.52</lowAsk>"
			      +"<lowBid>24.94</lowBid>"
			      +"<numTrades>0</numTrades>"
			      +"<open>0.0</open>"
			      +"<openInterest>0</openInterest>"
			      +"<optionStyle></optionStyle>"
			      +"<optionUnderlier></optionUnderlier>"
			      +"<prevClose>24.98</prevClose>"
			      +"<prevDayVolume>161301</prevDayVolume>"
			      +"<primaryExchange>u </primaryExchange>"
			      +"<symbolDesc>Audentes Therapeutics Inc</symbolDesc>"
			      +"<todayClose>0.0</todayClose>"
			      +"<totalVolume>0</totalVolume>"
			      +"<upc>0</upc>"
			      +"<volume10Day>122579</volume10Day>"
			    +"</all>"
			    +"<dateTime>16:26:23 EDT 09-18-2017</dateTime>"
			    +"<product>"
			    +"  <symbol>BOLD</symbol>"
			    +"  <type>EQ</type>"
			    +"  <exchange>u </exchange>"
			    +"</product>"
			  +"</QuoteData>"
			  + "</QuoteResponse>";
	}
	
	
	private static String getGBTC_QuoteResponse(){
		return "<QuoteResponse>"
				+"<QuoteData>"
				+"  <all>"
			      +"<adjNonAdjFlag>false</adjNonAdjFlag>"
			      +"<annualDividend>0.0</annualDividend>"
			      +"<ask>"+ask_counter+"</ask>"
			      +"<askExchange>V</askExchange>"
			      +"<askSize>1</askSize>"
			      +"<askTime>08:22:29 EDT 09-18-2017</askTime>"
			      +"<bid>"+bid_counter+"</bid>"
			      +"<bidExchange>V</bidExchange>"
			      +"<bidSize>1</bidSize>"
			      +"<bidTime>08:22:29 EDT 09-18-2017</bidTime>"
			      +"<chgClose>5.0</chgClose>"
			      +"<chgClosePrcn>"+chgClosePrcn_counter_GBTC+"</chgClosePrcn>"
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
    	
    	// Negative trend
//    	if(chgClosePrcn_counter<=15.0d)
//    		chgClosePrcn_counter=chgClosePrcn_counter-1.0d;
//    	else {
//    		chgClosePrcn_counter=Math.abs(chgClosePrcn_counter)+1.0d;
//    	}
//    	
//    	if(chgClosePrcn_counter<= -6.0){
//    		bid_counter= bid_counter-5.0;
//    		ask_counter= ask_counter-5.0;
//    	}
    	
    	// Positive trend    	
    	chgClosePrcn_counter_GBTC=chgClosePrcn_counter_GBTC+1.0D;
    	
    	if(chgClosePrcn_counter_GBTC>= 6.0D){
    		bid_counter= bid_counter+5.0;
    		ask_counter= ask_counter+5.0;
    	}
    	
    	//Positive and Negative trend both
//    	if(++counter<10){
//    		if(counter%2==0)
//    			chgClosePrcn_counter=chgClosePrcn_counter-2.75D;
//    		else
//    			chgClosePrcn_counter=chgClosePrcn_counter+1.0D;
//    	}
//    	else{
//    		if(counter%2==0)
//    			chgClosePrcn_counter=chgClosePrcn_counter+1.0D;
//    		else
//    			chgClosePrcn_counter=chgClosePrcn_counter-2.75D;
//    	}
//    	
//    	if(chgClosePrcn_counter>= 6.0D && chgClosePrcn_counter<=10.0d && counter<10){
//    		bid_counter= bid_counter+5.0D;
//    		ask_counter= ask_counter+5.0D;
//    	} else {
//    		bid_counter= bid_counter-5.0D;
//    		ask_counter= ask_counter-5.0D;
//    		
//    	}
//    	
    	
    	
    	
    	return getGBTC_QuoteResponse();
    }
    
    
    @RequestMapping("/market/rest/quote/BOLD")
    public String getQuoteResponseForBOLD() {
    	
    	
    	// Positive trend    	
    	chgClosePrcn_counter_BOLD=chgClosePrcn_counter_BOLD+1.0D;
    	
    	if(chgClosePrcn_counter_BOLD>= 6.0D){
    		bid_counter= bid_counter+5.0;
    		ask_counter= ask_counter+5.0;
    	}
    	
    	
    	return getBOLD_QuoteResponse();
    }    

}
