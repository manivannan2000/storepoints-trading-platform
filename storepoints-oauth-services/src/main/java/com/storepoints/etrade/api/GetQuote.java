package com.storepoints.etrade.api;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.storepoints.etrade.dto.GetQuoteParams;
import com.storepoints.etrade.model.EquityOrder;
import com.storepoints.etrade.model.ProfitTrend;
import com.storepoints.etrade.model.QuoteTrend;
import com.storepoints.etrade.research.MarketResearch;
import com.storepoints.exception.ApplicationException;

/**
 * a simple program to get quotes.
 * 
 * 
 */
public class GetQuote extends ApiBase{
	
	private static final Log log = LogFactory.getLog(GetQuote.class);

	private  String accessToken;
	private  String accessTokenSecret;
	
	private String serverHost;
	
	private String apiPath;
	
	private String detailFlag;
	
    private String accountId;
    
    private double bid;
    private double ask;
    
    private double chgClose;
    private double chgClosePrcn;
    private String orderQuote;
    private int quantity;
    private double chgClosePrcnLimit;
    private boolean taskComplete;
    
    private int counter=0;
    
    private long quoteSleepTime;
    
    private boolean wildCardQuote;
    
    private boolean marketResearch;
    
    
    private double profitPrcn;

    
    private static Map<String,EquityOrder> orderMap= new ConcurrentHashMap<String,EquityOrder>();
    
    private static Map<String,Integer> orderCounterMap= new ConcurrentHashMap<String,Integer>();
    
    
    private static Map<String,QuoteTrend> quoteTrendMap= new ConcurrentHashMap<String,QuoteTrend>();
    
    private static Map<String,ProfitTrend> profitTrendMap= new ConcurrentHashMap<String,ProfitTrend>();
    
    private static Map<String,Double> quoteAlreadyAddedChgClosePrcnMap= new ConcurrentHashMap<String,Double>();
    
	private static Queue<String> quoteBlockerQueue= new ConcurrentLinkedQueue<String>();
    
	// This queue helps to avoid boxed trades like BUY and SELL_SHORT in quick successions.
	private static Queue<String> orderInprogressQueue= new ConcurrentLinkedQueue<String>();
    
    
    public GetQuote(String accessToken,String accessTokenSecret, String accountId, String quotesList, String detailFlag, int quantity, double chgClosePrcnLimit, boolean wildCardQuote) {
    	this.accessToken=accessToken;
    	this.accessTokenSecret=accessTokenSecret;
//    	this.serverHost="etwssandbox.etrade.com";
//    	this.apiPath="/accounts/sandbox/rest/accountlist";
    	this.serverHost=getServerHostConfig();
    	this.apiPath="/market/rest/quote/"+quotesList;
    	this.accountId=accountId;
    	this.orderQuote=quotesList;
    	this.detailFlag=detailFlag;
    	this.quantity=quantity;
    	this.chgClosePrcnLimit=chgClosePrcnLimit;
    	this.wildCardQuote=wildCardQuote;
    	calculateQuoteSleepTime();
    }
    
    public GetQuote(GetQuoteParams quoteParams) {
    	this.accessToken=quoteParams.getAccessToken();
    	this.accessTokenSecret=quoteParams.getAccessTokenSecret();
    	this.serverHost=getServerHostConfig();
    	this.apiPath="/market/rest/quote/"+quoteParams.getQuotesList();
    	this.accountId=quoteParams.getAccountId();
    	this.orderQuote=quoteParams.getQuotesList();
    	this.detailFlag=quoteParams.getDetailFlag();
    	this.quantity=quoteParams.getQuantity();
    	this.chgClosePrcnLimit=quoteParams.getChgClosePrcnLimit();
    	this.wildCardQuote=quoteParams.isWildCardQuote();
    	this.marketResearch=quoteParams.isMarketResearch();
    	calculateQuoteSleepTime();
    }
    
    
    
    public  void quote() throws ClientProtocolException,
    IOException, URISyntaxException, InvalidKeyException,
    NoSuchAlgorithmException {
    	
    	String getQuoteResponse=null;
    	
    	
        HttpClient httpclient = new DefaultHttpClient();
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        // These params should ordered in key
//        qparams.add(new BasicNameValuePair("oauth_callback", "oob"));
        qparams.add(new BasicNameValuePair("detailFlag", detailFlag));

        qparams.add(new BasicNameValuePair("oauth_consumer_key", getConsumerKey()));
        qparams.add(new BasicNameValuePair("oauth_nonce", ""
                + (int) (Math.random() * 100000000)));
        qparams.add(new BasicNameValuePair("oauth_signature_method",
                "HMAC-SHA1"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", ""
                + (System.currentTimeMillis() / 1000)));
//        qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
        qparams.add(new BasicNameValuePair("oauth_token", getAccessToken()));
        
        // generate the oauth_signature
        String signature = getSignature("GET", URLEncoder.encode(
        		getServerHostProtocol()+"://"+getServerHost()+getApiPath(), ENC),
                URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC), false);

        // add it to params list
        qparams.add(new BasicNameValuePair("oauth_signature", signature));

        // generate URI which lead to access_token and token_secret.
        URI uri = URIUtils.createURI(getServerHostProtocol(), getServerHost(), -1,
        		getApiPath(),
                URLEncodedUtils.format(qparams, ENC), null);

//        log.info("Get Quote from:"
//                + uri.toString());

        HttpGet httpget = new HttpGet(uri);
        // output the response content.
//        log.info("Get Quote Response for Quote ["+orderQuote+"]:");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            int len;
            byte[] tmp = new byte[2048];
            while ((len = instream.read(tmp)) != -1) {
            	getQuoteResponse=new String(tmp, 0, len, ENC);
            }
        }
        
//        log.info(getQuoteResponse);
        
        try{
            setAsk(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<ask>")+5,getQuoteResponse.indexOf("</ask>"))));
            setBid(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<bid>")+5,getQuoteResponse.indexOf("</bid>"))));
            setChgClose(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<chgClose>")+10,getQuoteResponse.indexOf("</chgClose>"))));
            
            setChgClosePrcn(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<chgClosePrcn>")+14,getQuoteResponse.indexOf("</chgClosePrcn>"))));
            
            log.info("Quote ["+orderQuote+"] Ask value:"+getAsk());
            log.info("Quote ["+orderQuote+"] Bid value:"+getBid());
            log.info("Quote ["+orderQuote+"] Change Close:"+getChgClose());
            
            log.info("Quote ["+orderQuote+"] Change Close Percentage:"+getChgClosePrcn());
            
            setAlreadyAddedChgClosePrcn(getChgClosePrcn());
            
    		setWildCardQuoteSleepTime();

            if(isMarketResearch()){
            	MarketResearch marketResearch= MarketResearch.getInstance();
            	marketResearch.addQuote(orderQuote, getChgClosePrcn());
            	
            }else if(!isOrderAlreadyPlaced() && !isOrdersExceedDailyLimit() && !isQuoteInBlockerQueue() && !isOrderInprogress() && isGoodOrderPlacingTime()){
            	
                if((isWildCardQuote() && !isRegularSessionTime() &&  (this.chgClosePrcnLimit>0 && getReconcilableChgClosePrcn() >=this.chgClosePrcnLimit) || (this.chgClosePrcnLimit<0 && getReconcilableChgClosePrcn() <=this.chgClosePrcnLimit )) ||
                (!isWildCardQuote() && (this.chgClosePrcnLimit>0 && getReconcilableChgClosePrcn() >=this.chgClosePrcnLimit) || (this.chgClosePrcnLimit<0 && getReconcilableChgClosePrcn() <=this.chgClosePrcnLimit ))){
                	if(this.chgClosePrcnLimit>0 && getReconcilableChgClosePrcn() >=(this.chgClosePrcnLimit*3)/2 && counter<WAIT_COUNTER){
                		log.info("Highly shooted up Quote ["+orderQuote+"]. Waiting to meet reasonable price limit percentage ["+this.chgClosePrcnLimit+"] for ["+(++counter)+"] times...");
                	} else if(this.chgClosePrcnLimit<0 && getReconcilableChgClosePrcn() <=(this.chgClosePrcnLimit*3)/2 && counter<WAIT_COUNTER){
                		log.info("Collapsing badly Quote ["+orderQuote+"]. Waiting to meet reasonable price limit percentage ["+this.chgClosePrcnLimit+"] for ["+(++counter)+"] times...");
                	} else {
                		placeOrder(false);
                	}
                }else{
                	if(new Long(quoteSleepTime).equals(getWILD_CARD_QUOTE_SLEEP_TIME_INIT()) && isRegularSessionTime() && isQuoteTrending()){
            			log.info("Trying to place order based on trending status of quote for "+ this.orderQuote+ " for quantity of "+this.quantity);
            			placeOrder(false);
                		
                	} else{
                    	log.info("Not ready to place Order for Quote ["+orderQuote+"]. Waiting to meet price limit percentage ["+this.chgClosePrcnLimit+"]...");
                	}
                }
            } else {
            	if(isOrderClosingRequiredConfig() && isWildCardQuote()){
            		checkAndPlaceCloseOrder(true);	
            	} else {
            		log.info("Order closing configuration currently disabled. Please check the configuration.");
            	}
            	
            	
            }
            
            
        }catch(UnknownHostException excp){    
        	excp.printStackTrace();
        	setTaskComplete(true);
        } catch(Throwable excp){
        	excp.printStackTrace();
        	log.error("Error occured:"+excp.getMessage());
        	log.error("Error Quote Respone:"+getQuoteResponse);
        	
        }
        
		if(isAfterHoursSessionCloseTime()){
			setTaskComplete(true);
		}
    }
    
    private void setWildCardQuoteSleepTime(){
    	if(isMarketResearch())
    		this.setQuoteSleepTime(300000);
    	else if(isWildCardQuote() && !isRegularSessionTime())
	    	this.setQuoteSleepTime(getWILD_CARD_QUOTE_SLEEP_TIME_ACTIVE());
	    else if(isWildCardQuote() && isRegularSessionTime())
			this.setQuoteSleepTime(getWILD_CARD_QUOTE_SLEEP_TIME_INIT());
    }
    
    
    private void placeOrder(boolean closeOrder) throws InvalidKeyException, ClientProtocolException, NoSuchAlgorithmException, IOException, URISyntaxException{
    	
//    	log.info("Go and Place Order for "+ this.orderQuote+ " for quantity of "+this.quantity +" and price:"+(this.chgClosePrcnLimit<0?this.ask:this.bid));
    	// As SELL_SHORT did not work immediately for ask price, changing to bid price and checking.
//    	log.info("Go and Place Order for "+ this.orderQuote+ " for quantity of "+this.quantity +" and price:"+this.bid);
		double limitPrice= getMedianLimitPrice();
		
		if((limitPrice-this.bid)*this.quantity > 20.0D){
			log.info("Placing order at this time is not good as ask and bid difference is more for order "+ this.orderQuote+ " for quantity of "+this.quantity +" and price:"+limitPrice);
		} else {
    		log.info("Go and Place Order for "+ this.orderQuote+ " for quantity of "+this.quantity +" and price:"+limitPrice);
            	
        	if(isOrderAlreadyPlaced() && !isWildCardQuote()){
        		checkAndPlaceCourseCorrectionOrders(closeOrder);
        	} else{
            	// As SELL_SHORT did not work immediately for ask price, changing as per this calculation and checking.
        		placeEquityOrder(this.accessToken,this.accessTokenSecret,this.accountId,this.orderQuote,this.quantity,limitPrice, null, closeOrder);
        	}
		}
    }
    
    
    private double getMedianLimitPrice() {
    	return getDoubleDecimalFormat((this.chgClosePrcnLimit<0?(this.bid+((this.ask-this.bid)/2)):this.bid));
    }
    
    
    private boolean isOrderAlreadyPlaced(){
    	boolean orderAlreadyPlaced=false;
    	if(orderMap.containsKey(getOrderMapKey())){
    		log.info("Order already placed and order is of type:"+orderMap.get(getOrderMapKey()));
    		orderAlreadyPlaced=true;
    	}
    	return orderAlreadyPlaced;
    }
    
    private boolean isOrdersExceedDailyLimit(){
    	boolean ordersExceedDailyLimit=false;
    	int orderDailyLimit=getOrderDailyLimit();
    	
    	final AtomicInteger count = new AtomicInteger();
    	
		orderMap.entrySet().stream().forEach((entry) -> {
		    String quote = entry.getKey();
		    EquityOrder equityOrder = entry.getValue();
			if(equityOrder.getOrderAction().equals("BUY") || equityOrder.getOrderAction().equals("SELL_SHORT"))
				count.incrementAndGet();
		});

		if(count.get()>=orderDailyLimit){
			if(isQuoteTrending() && isTrendFrequencyHigh()){
				log.info("Quote ["+orderQuote+"] Orders exceed the daily limit. But trend frequency is high. So exception applied for this quote.");
			}else {
				ordersExceedDailyLimit=true;
				log.info("Orders exceed the daily limit.");
			}
		}
    	
    	return ordersExceedDailyLimit;
    }
    
    
    private void setOrderInprogressStatus(EquityOrder equityOrder){
    	
		if(equityOrder.getOrderAction().equals("BUY") || equityOrder.getOrderAction().equals("SELL_SHORT")){
			orderInprogressQueue.add(orderQuote);
			log.info("Quote ["+orderQuote+"] is added to Order inprogress queue as open orders applied.");

		}else {
			if(orderInprogressQueue.contains(orderQuote)){
				log.info("Quote ["+orderQuote+"] is removed from Order in-progress queue as close orders applied.");
				orderInprogressQueue.remove(orderQuote);
			}
			
			if(quoteAlreadyAddedChgClosePrcnMap.containsKey(orderQuote)){
				log.info("Quote ["+orderQuote+"] already added change close percentage value cleared now as close orders applied.");
				quoteAlreadyAddedChgClosePrcnMap.remove(orderQuote);
				
			}
		}
    }
    
    private boolean isOrderInprogress(){
    	boolean result=orderInprogressQueue.contains(orderQuote);
    	
    	if(result){
			log.info("Quote ["+orderQuote+"] is already placed in order in-progress queue. so no more placing of trades for change Close Percent Limit ["+this.chgClosePrcnLimit+"]");
    	}
    	
    	return result;


    }
    
    private boolean isQuoteInBlockerQueue(){
    	boolean result=quoteBlockerQueue.contains(orderQuote);
    	
    	if(result){
			log.info("Quote ["+orderQuote+"] is already placed in blocker queue. so no more placing of trades for change Close Percent Limit ["+this.chgClosePrcnLimit+"]");
    	}
    	return result;
    }
    
    
    private boolean isGoodOrderPlacingTime(){
    	boolean result=false;
    	
        LocalTime thisTime=LocalTime.now();

    	if(thisTime.getHour()<getFavorableOrderPlaceMaxHour()){
    		result =true;
    	} else {
    		log.info("Quote ["+orderQuote+"] favorable order placing time exceeds so no more orders placed after this time for ["+this.chgClosePrcnLimit+"]");
    	}
    	return result;
    }
    
    
    
    
    private boolean isTrendFrequencyHigh(){
    	boolean trendFrequencyHigh=false;
    	if(quoteTrendMap.containsKey(getQuoteTrendMapKey())){
    		QuoteTrend quoteTrend=quoteTrendMap.get(getQuoteTrendMapKey());

    		if(quoteTrend.getTrendCounter()>=10)
    			trendFrequencyHigh=true;
    	}
    	
    	return trendFrequencyHigh;
    }
    
    
    
    private boolean isQuoteTrending(){
    	boolean quoteTrending=false;
    	
    	if(quoteTrendMap.containsKey(getQuoteTrendMapKey())){
    		QuoteTrend quoteTrend=quoteTrendMap.get(getQuoteTrendMapKey());
    		quoteTrend.setPrevChgClosePrcn(getReconcilableChgClosePrcn());
    		
    		double climbCounter=quoteTrend.getClimbCounter();
    		double fallCounter=quoteTrend.getFallCounter();
    		double diffClimbCounter=climbCounter-fallCounter;
    		double diffFallCounter=fallCounter-climbCounter;
    		
    		log.info("Quote ["+orderQuote+"] Trend Climb Counter :"+climbCounter);
    		log.info("Quote ["+orderQuote+"] Trend Fall Counter :"+fallCounter);
    		
    		
    		
    		if(	quoteTrend.isQueueFiftyPercentFilled() && 

    				(    				// Within configured chgClosePrcnLimit range
    						(this.chgClosePrcnLimit>0 && diffClimbCounter > 0 && diffClimbCounter>=this.chgClosePrcnLimit/4 && diffClimbCounter<=(this.chgClosePrcnLimit*3)/4 )  ||
    						    // With double of configured chgClosePrcnLimit range
    						(this.chgClosePrcnLimit>0 && diffClimbCounter > 0 && diffClimbCounter>=(this.chgClosePrcnLimit*5)/4 && diffClimbCounter<=(this.chgClosePrcnLimit*7)/4 )  ||
		    					// With triple of configured chgClosePrcnLimit range
    						(this.chgClosePrcnLimit>0 && diffClimbCounter > 0 && diffClimbCounter>=(this.chgClosePrcnLimit*9)/4 && diffClimbCounter<=(this.chgClosePrcnLimit*11)/4 )  ||
    						(this.chgClosePrcnLimit>0 && (diffClimbCounter < 0  && climbCounter>=(this.chgClosePrcnLimit*3)/8 && Math.abs(diffClimbCounter)>=(this.chgClosePrcnLimit*3)/4))  ||
    									// Within configured chgClosePrcnLimit range
    						(this.chgClosePrcnLimit<0 && (diffFallCounter > 0 && diffFallCounter>=Math.abs(this.chgClosePrcnLimit/4) && diffFallCounter<=Math.abs((this.chgClosePrcnLimit*3)/4)))||
    									// With double of configured chgClosePrcnLimit range
    						(this.chgClosePrcnLimit<0 && (diffFallCounter > 0 && diffFallCounter>=Math.abs((this.chgClosePrcnLimit*5)/4) && diffFallCounter<=Math.abs((this.chgClosePrcnLimit*7)/4)))||
    							// With triple of configured chgClosePrcnLimit range
    						(this.chgClosePrcnLimit<0 && (diffFallCounter > 0 && diffFallCounter>=Math.abs((this.chgClosePrcnLimit*9)/4) && diffFallCounter<=Math.abs((this.chgClosePrcnLimit*11)/4)))||
    						(this.chgClosePrcnLimit<0 && (diffFallCounter < 0  && fallCounter>=Math.abs((this.chgClosePrcnLimit*3)/8) && diffFallCounter<=(this.chgClosePrcnLimit*3)/4)) // ||
    				)) {
    			quoteTrending=true;
    			quoteTrend.setTrendCounter(quoteTrend.getTrendCounter()+1);
    		} else {
    			quoteTrend.setTrendCounter(0);
    		}
    		
    		quoteTrendMap.put(getQuoteTrendMapKey(),quoteTrend);
    		
    	} else {
    		quoteTrendMap.put(getQuoteTrendMapKey(), new QuoteTrend(this.getReconcilableChgClosePrcn(),0));
    	}
    	
    	return quoteTrending;
    }
    
    
    private boolean isProfitTrendDecline(){
    	boolean profitTrendDecline=false;
    	
    	if(profitTrendMap.containsKey(getProfitTrendMapKey())){
    		ProfitTrend profitTrend=profitTrendMap.get(getProfitTrendMapKey());
    		profitTrend.setPrevProfitPrcn(getProfitPrcn());
    		
    		double climbCounter=profitTrend.getClimbCounter();
    		double fallCounter=profitTrend.getFallCounter();
    		double diffClimbCounter=climbCounter-fallCounter;
    		double diffFallCounter=fallCounter-climbCounter;
    		
    		log.info("Profit ["+orderQuote+"] Trend Climb Counter :"+climbCounter);
    		log.info("Profit ["+orderQuote+"] Trend Fall Counter :"+fallCounter);
    		
    		if(		(this.chgClosePrcnLimit>0 && diffClimbCounter > 0 && climbCounter>0 && fallCounter>=(climbCounter * 0.5D) && getProfitPrcn()>=10.0D)  ||
    				(this.chgClosePrcnLimit>0 && diffClimbCounter < 0  && climbCounter>0 && ( fallCounter>=climbCounter*2 || getProfitPrcn()<=-4.0D )) || 
    				(this.chgClosePrcnLimit<0 && diffFallCounter > 0 && fallCounter>0 && (fallCounter>=climbCounter*2 || getProfitPrcn()<=-4.0D ))||
    				(this.chgClosePrcnLimit<0 && diffFallCounter < 0  && fallCounter>0 && fallCounter>=(climbCounter*0.5D) && getProfitPrcn()>=10.0D) 
    				) {
    			profitTrendDecline=true;
    		} 
    		
    		profitTrendMap.put(getProfitTrendMapKey(),profitTrend);
    		
    	} else {
    		profitTrendMap.put(getProfitTrendMapKey(), new ProfitTrend(this.getProfitPrcn()));
    	}
    	
    	if(profitTrendDecline){
    		
    		log.info("Profit ["+orderQuote+"] Trend declining for ["+this.chgClosePrcnLimit+"].");
    	}
    	
    	return profitTrendDecline;
    }    
    
    
    private void checkAndPlaceCloseOrder(boolean closeOrder) throws InvalidKeyException, ClientProtocolException, NoSuchAlgorithmException, IOException, URISyntaxException{
    	
    	boolean orderCloseEnforcementTrigger=false;
    	if(orderMap.containsKey(getOrderMapKey())){
    		EquityOrder equityOrder = orderMap.get(getOrderMapKey());
    		
    		if(closeOrder && equityOrder.isOrderAlreadyClosed()){
    			log.info("Order already closed for "+equityOrder+"  and counter: "+equityOrder.getOrderClosedCounter());
    			equityOrder.setOrderClosedCounter(equityOrder.getOrderClosedCounter()+1);
    			
    			if(equityOrder.getOrderClosedCounter()>(20 * (orderCounterMap.get(getOrderMapKey())==null?1:orderCounterMap.get(getOrderMapKey())))){
    				orderMap.remove(getOrderMapKey());
    			} else{
    				orderMap.put(getOrderMapKey(),
    						equityOrder);
    			}
    				
    			return;
    		}
    		
    		if(isOrderCloseRequired(equityOrder, closeOrder, orderCloseEnforcementTrigger)){
    			
    			String closeOrderAction=null;
    			
    			if("BUY".equals(equityOrder.getOrderAction())){
    				closeOrderAction="SELL";
    			} else{
    				closeOrderAction="BUY_TO_COVER";
    			}
    				
    			
    			log.info("Trying to place close order  for : "+equityOrder);

    			
    			placeEquityOrder(this.accessToken,this.accessTokenSecret,this.accountId,this.orderQuote,this.quantity,(this.chgClosePrcnLimit<0?this.bid:this.ask), closeOrderAction, closeOrder);
    		}
    	}    	
    	
    }
    
    
    private void checkAndPlaceCourseCorrectionOrders(boolean closeOrder) throws InvalidKeyException, ClientProtocolException, NoSuchAlgorithmException, IOException, URISyntaxException{
    	
    	if(orderMap.containsKey(getOrderMapKey())){
    		EquityOrder equityOrder = orderMap.get(getOrderMapKey());
    		
    		if(closeOrder && equityOrder.isOrderAlreadyClosed()){
    			log.info("Order already closed for "+equityOrder);
    			return;
    		}
    		
    		if(isCourseCorrectionRequired(equityOrder, closeOrder)){
    			
    			String courseCorrectionOrderAction=null;
    			String courseCorrectionSecondOrderAction=null;
    			
    			if("BUY".equals(equityOrder.getOrderAction())){
    				courseCorrectionOrderAction="SELL";
    				courseCorrectionSecondOrderAction="SELL_SHORT";
    			} else{
    				courseCorrectionOrderAction="BUY_TO_COVER";
    				courseCorrectionSecondOrderAction="BUY";
    			}
    				
    			
    			placeEquityOrder(this.accessToken,this.accessTokenSecret,this.accountId,this.orderQuote,this.quantity,(this.chgClosePrcnLimit<0?this.bid:this.ask), courseCorrectionOrderAction, closeOrder);
//    			if(!closeOrder){
//        			// placing second order immediately to gain some course correction impact. This may or may not work. Please revisit after week of observation - Week dated: 09/25/2017.
//        			placeEquityOrder(this.accessToken,this.accessTokenSecret,this.accountId,this.orderQuote,this.quantity,(this.chgClosePrcnLimit<0?this.bid:this.ask), courseCorrectionSecondOrderAction, false);
//    			}
    		}
    	}
    }
    
    
    private boolean isOrderCloseRequired(EquityOrder equityOrder, boolean closeOrder, boolean orderCloseEnforcementTrigger){
 	   boolean orderCloseRequired=false;
	   
 	   if(closeOrder && isOrderClosingRequired(equityOrder, orderCloseEnforcementTrigger)){
 		  orderCloseRequired=true;
 	   }
 	   
 	   if(isQuoteTrending() && !orderCloseEnforcementTrigger){
 		  orderCloseRequired=false;
 		 log.info("Quote ["+orderQuote+"] is still trending so order close cannot be applied. Please check back later for "+equityOrder);
 	   }
 	   
 	   
 	   if(!equityOrder.isOrderAlreadyClosed() && orderCloseRequired)
 		   log.info((closeOrder?"Close Order":"Course correction")+" not required at this point of time for "+equityOrder);
 	   
 	   return orderCloseRequired;
    }
    
   private boolean isCourseCorrectionRequired(EquityOrder equityOrder, boolean closeOrder){
	   boolean courseCorrectionRequired=false;
	   boolean orderCloseEnforcementTrigger=false;
	   
	   if(closeOrder && isOrderClosingRequired(equityOrder, orderCloseEnforcementTrigger)){
		   courseCorrectionRequired=true;
	   }else{
			if("BUY".equals(equityOrder.getOrderAction())){
				if(getReconcilableChgClosePrcn() <=this.chgClosePrcnLimit/2){
					courseCorrectionRequired=true;
				} 
			} else {
				if(getReconcilableChgClosePrcn() >=this.chgClosePrcnLimit/2){
					courseCorrectionRequired=true;
				}
			}
	   }
	   
	   
		if(!courseCorrectionRequired){
			log.info((closeOrder?"Close Order":"Course correction")+" not required at this point of time for "+equityOrder);
		}
	   
	   return courseCorrectionRequired;
   }
   
   
   
   private boolean isOrderClosingRequired(EquityOrder equityOrder, boolean orderCloseEnforcementTrigger){
	   boolean orderClosingRequired=false;
	   double percentIncreaseNow=0.0d;
	   
	   double closingOrderPrice=getMedianLimitPrice();
	   double estimatedTotalGain=0.0D;
	   
	   if(!equityOrder.isOrderAlreadyClosed()){
		   double priceExecuted=equityOrder.getPriceType().equals("MARKET")?equityOrder.getEstimatedMarketPrice():equityOrder.getLimitPrice();

		   if(this.chgClosePrcnLimit>0){
			   percentIncreaseNow=getDoubleDecimalFormat((closingOrderPrice-priceExecuted)*100/priceExecuted);
			   estimatedTotalGain= (closingOrderPrice-priceExecuted)*equityOrder.getQuantity();
		   } else if(this.chgClosePrcnLimit<0){
			   percentIncreaseNow=getDoubleDecimalFormat((priceExecuted-closingOrderPrice)*100/priceExecuted);
			   estimatedTotalGain= (priceExecuted-closingOrderPrice)*equityOrder.getQuantity();
		   }
		   
		   log.info("Percent Increase for order symbol ["+equityOrder.getSymbol()+"] and chgClosePrcnLimit ["+this.chgClosePrcnLimit+"]:"+percentIncreaseNow);
		   setProfitPrcn(percentIncreaseNow);
		   log.info("Estimated Total Gain for order symbol ["+equityOrder.getSymbol()+"] and chgClosePrcnLimit ["+this.chgClosePrcnLimit+"]:"+estimatedTotalGain);
		   
		   LocalTime orderTime=equityOrder.getOrderTime();
		   LocalTime orderTimePlusMins= orderTime.plusMinutes(getOrderClosingMinutes());
		   LocalTime orderTimePlusHour= orderTime.plusHours(1L);
		   LocalTime orderTimePlusThreeHours= orderTime.plusHours(3L);
		   
		   //Anything exceeds more than 300, trigger close order immediately. Also, any loss exceeds more than 200, go for immediate order closing.
		   if(BigDecimal.valueOf(estimatedTotalGain).compareTo(BigDecimal.valueOf(300.0d))>0 || BigDecimal.valueOf(estimatedTotalGain).compareTo(BigDecimal.valueOf(-200.0d))<0 ){
			   log.info("Triggering immediate order close for :"+equityOrder.toString());
			   orderCloseEnforcementTrigger=true;
			   orderClosingRequired=true;
		   }else if( ((isProfitTrendDecline() || BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(24.0d))>0  && BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(36.0d))< 0 ) && LocalTime.now().isAfter(orderTimePlusMins))  ||
			  (BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(16.0d))>0  &&  BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(20.0d))< 0  && LocalTime.now().isAfter(orderTimePlusHour)) ||
			  (BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(10.0d))>0  &&  BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(14.0d))< 0  && LocalTime.now().isAfter(orderTimePlusThreeHours)) ||
			  // Currently commented out to follow Profit Trend Decline or isOrderTrending status.
			  //			  isOrderClosingBasedOnFlatRate(equityOrder, estimatedTotalGain) ||
			  (BigDecimal.valueOf(percentIncreaseNow).compareTo(BigDecimal.valueOf(0.25d))>0 && isRegularSessionCloseTime())){
			   orderClosingRequired=true;
		   } 
	   
	   } else {
		   log.info("Order already closed for :"+equityOrder.toString());
	   }

	   
	   return orderClosingRequired;
   }
   
   
   private boolean isOrderClosingBasedOnFlatRate(EquityOrder equityOrder, double estimatedTotalGain){
	   boolean orderClosingBasedOnFlatRate=false;
	   
	   if(Double.compare(estimatedTotalGain, getOrderClosingFlatRate())>=0){
		   orderClosingBasedOnFlatRate=true;
		   log.info("Order closing based on flat rate applied for :"+equityOrder.toString());
	   }
	   
	   return orderClosingBasedOnFlatRate;
   }
    
    
    private  void placeEquityOrder(String accessToken, String accessTokenSecret,String accountId, String orderQuote,int quantity, double limitPrice, String courseCorrectionOrderAction , boolean closeOrder) throws InvalidKeyException, ClientProtocolException, NoSuchAlgorithmException, IOException, URISyntaxException{
    	EquityOrder equityOrder= new EquityOrder();
        equityOrder.setAccountId(Integer.parseInt(accountId));
        equityOrder.setSymbol(orderQuote);
        if(courseCorrectionOrderAction==null)
        	equityOrder.setOrderAction(this.chgClosePrcnLimit<0? "SELL_SHORT":"BUY");
        else
        	equityOrder.setOrderAction(courseCorrectionOrderAction);
        equityOrder.setClientOrderId(""+(int) (Math.random() * 10000));
        equityOrder.setQuantity(quantity);
        
        // @ TODO Market orders are applied as try out. Revisit and put back Limit orders and prepare for Order edits using list orders.

		if(isRegularSessionTime()) {
			equityOrder.setMarketSession("REGULAR");
	        equityOrder.setPriceType("MARKET");
	        equityOrder.setEstimatedMarketPrice(limitPrice);
		} else{
			equityOrder.setMarketSession("EXTENDED");	
	        equityOrder.setPriceType("LIMIT");
	        equityOrder.setLimitPrice(limitPrice);
		}
        
        equityOrder.setOrderTerm("GOOD_FOR_DAY");
        
        PlaceEquityOrderUtil placeEquityOrderUtil= new PlaceEquityOrderUtil(accessToken, accessTokenSecret, equityOrder);
        
		try {
			// Applying throttle to get response.
			Thread.sleep(3000);
			
			placeEquityOrderUtil.orderEquity();
			
			Thread.sleep(3000);
			
			log.info((closeOrder?"CLOSE ":"")+(courseCorrectionOrderAction!=null?"COURSE CORRECTION ":"")+"Order placed for " + this.orderQuote
					+ " for quantity of " + this.quantity + " and price:"
					+ limitPrice + " and orderNumber:"
					+ placeEquityOrderUtil.getOrderNum());
			
			equityOrder.setOrderNum(placeEquityOrderUtil.getOrderNum());
			equityOrder.setOrderTime(LocalTime.now());
			if(closeOrder){
				equityOrder.setOrderAlreadyClosed(true);	
				equityOrder.setOrderClosedCounter(1);
			}
			if((closeOrder && courseCorrectionOrderAction!=null) ||(!closeOrder && courseCorrectionOrderAction==null)){
				orderMap.put(getOrderMapKey(),
						equityOrder);
				
				if(orderCounterMap.containsKey(getOrderMapKey())){
					orderCounterMap.put(getOrderMapKey(), orderCounterMap.get(getOrderMapKey())+1);
				} else{
					orderCounterMap.put(getOrderMapKey(), new Integer(1));	
				}
				
				setOrderInprogressStatus(equityOrder);
				
				
			} else {
				// 	Order Map removal not required as it is placing orders again and again. Please revisit this dangerous logic in test harness systems.
//				if(orderMap.containsKey(getOrderMapKey()))
//					orderMap.remove(getOrderMapKey());
			}
			
			//Resetting counter after placing order.
        	counter=0;
			
		} catch (ApplicationException aExcp){ 	

			log.error("Failure occured due to application issue of errorCode["+aExcp.getErrorCode()+"] and errorMessage["+aExcp.getErrorMessage()+"] for "
					+ this.orderQuote + " for quantity of " + this.quantity
					+ " and price:" + limitPrice);
			
			if(aExcp.getErrorCode()==1516 && orderMap.containsKey(getOrderMapKey())){
				orderMap.remove(getOrderMapKey());
			} else if(aExcp.getErrorCode()==6510){
				quoteBlockerQueue.add(orderQuote);
			}
		} catch (Throwable excp) {
			log.error(excp.getMessage());
			log.error("Failure occured while placing trade for "
					+ this.orderQuote + " for quantity of " + this.quantity
					+ " and price:" + limitPrice);
		}
    }
    
    
    private void calculateQuoteSleepTime(){
    	
    	if(wildCardQuote){
    		setQuoteSleepTime(getWILD_CARD_QUOTE_SLEEP_TIME_INIT());
    	} else {
    		setQuoteSleepTime(30000L);
    	}
    }
    
    private String getQuoteTrendMapKey(){
    	return getTrendMapKey();
    }
    
    private String getProfitTrendMapKey(){
    	return getTrendMapKey();
    }
    
    
    private String getTrendMapKey(){
    	return this.orderQuote+"_"+String.valueOf(this.chgClosePrcnLimit);
    }
    
    
    
    
    private String getOrderMapKey(){
    	// Enable this logic whenever you see more hedging happening and wanted to avoid. Note: Very dangerous logic. Revisit.
//    	return this.orderQuote+"_"+this.quantity+"_"+String.valueOf(Math.abs(this.chgClosePrcnLimit));
    	return this.orderQuote+"_"+this.quantity+"_"+String.valueOf(this.chgClosePrcnLimit);
    	
    }



	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}


	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}


	public String getServerHost() {
		return serverHost;
	}


	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}


	public String getApiPath() {
		return apiPath;
	}


	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}


	public double getBid() {
		return bid;
	}


	public void setBid(double bid) {
		this.bid = bid;
	}


	public double getAsk() {
		return ask;
	}


	public void setAsk(double ask) {
		this.ask = ask;
	}


	public double getChgClose() {
		return chgClose;
	}


	public void setChgClose(double chgClose) {
		this.chgClose = chgClose;
	}


	public double getChgClosePrcn() {
		return chgClosePrcn;
	}
	
	public double getReconcilableChgClosePrcn() {
		return chgClosePrcn - (quoteAlreadyAddedChgClosePrcnMap.containsKey(orderQuote)?quoteAlreadyAddedChgClosePrcnMap.get(orderQuote):0.0D);
	}
	


	public void setChgClosePrcn(double chgClosePrcn) {
		this.chgClosePrcn = chgClosePrcn;
	}
	
	public void setAlreadyAddedChgClosePrcn(double chgClosePrcn) {
		if(!quoteAlreadyAddedChgClosePrcnMap.containsKey(orderQuote) || chgClosePrcn==0.0D || quoteAlreadyAddedChgClosePrcnMap.get(orderQuote)==0.0D){
			quoteAlreadyAddedChgClosePrcnMap.put(orderQuote, chgClosePrcn);
			log.info("Quote ["+orderQuote+"] Already Added Change Close Percentage value set to ["+chgClosePrcn+"]");
		}
		
	}
	

	
	

	public boolean isTaskComplete() {
		return taskComplete;
	}


	public void setTaskComplete(boolean taskComplete) {
		this.taskComplete = taskComplete;
	}


	public long getQuoteSleepTime() {
		return quoteSleepTime;
	}


	public void setQuoteSleepTime(long quoteSleepTime) {
		this.quoteSleepTime = quoteSleepTime;
	}


	public boolean isWildCardQuote() {
		return wildCardQuote;
	}


	public void setWildCardQuote(boolean wildCardQuote) {
		this.wildCardQuote = wildCardQuote;
	}


	public double getProfitPrcn() {
		return profitPrcn;
	}


	public void setProfitPrcn(double profitPrcn) {
		this.profitPrcn = profitPrcn;
	}


	@Override
	protected String getTokenSecret() {
		return this.accessTokenSecret;
	}

	public boolean isMarketResearch() {
		return marketResearch;
	}

	public void setMarketResearch(boolean marketResearch) {
		this.marketResearch = marketResearch;
	}
}

