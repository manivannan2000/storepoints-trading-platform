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
public class GetOptionChains extends ApiBase{
	
	private static final Log log = LogFactory.getLog(GetOptionChains.class);

	private  String accessToken;
	private  String accessTokenSecret;
	
	private String serverHost;
	
	private String apiPath;
	
    private String chainType;
    private int expirationMonth;
    private int expirationYear;
    private String underlier;
    
    private long optionChainsSleepTime;
    
    private boolean taskComplete;
    
    
    
    public GetOptionChains(String accessToken,String accessTokenSecret, String chainType, int expirationMonth, int expirationYear, String underlier) {
    	this.accessToken=accessToken;
    	this.accessTokenSecret=accessTokenSecret;
    	this.serverHost=getServerHostConfig();
    	this.chainType=chainType;
    	this.expirationMonth=expirationMonth;
    	this.expirationYear=expirationYear;
    	this.underlier=underlier;
    	this.setOptionChainsSleepTime(5000L);
    	this.apiPath="/market/rest/optionchains";
    }
    
    
    public  void optionChains() throws ClientProtocolException,
    IOException, URISyntaxException, InvalidKeyException,
    NoSuchAlgorithmException {
    	
    	String optionChainsResponse=null;
    	
    	
        HttpClient httpclient = new DefaultHttpClient();
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        // These params should ordered in key
//        qparams.add(new BasicNameValuePair("oauth_callback", "oob"));
        qparams.add(new BasicNameValuePair("chainType", chainType));
        qparams.add(new BasicNameValuePair("expirationMonth", ""+expirationMonth));
        qparams.add(new BasicNameValuePair("expirationYear", ""+expirationYear));
        qparams.add(new BasicNameValuePair("underlier", underlier));
        qparams.add(new BasicNameValuePair("skipAdjusted","true"));
        
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
                URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC), true);

        // add it to params list
        qparams.add(new BasicNameValuePair("oauth_signature", signature));

        // generate URI which lead to access_token and token_secret.
        URI uri = URIUtils.createURI(getServerHostProtocol(), getServerHost(), -1,
        		getApiPath(),
                URLEncodedUtils.format(qparams, ENC), null);

        log.info("Get Option Chains from:"
                + uri.toString());

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
            	optionChainsResponse=new String(tmp, 0, len, ENC);
            }
        }
        
       log.info(optionChainsResponse);
        
//        try{
//            setAsk(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<ask>")+5,getQuoteResponse.indexOf("</ask>"))));
//            setBid(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<bid>")+5,getQuoteResponse.indexOf("</bid>"))));
//            setChgClose(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<chgClose>")+10,getQuoteResponse.indexOf("</chgClose>"))));
//            
//            setChgClosePrcn(Double.parseDouble(getQuoteResponse.substring(getQuoteResponse.indexOf("<chgClosePrcn>")+14,getQuoteResponse.indexOf("</chgClosePrcn>"))));
//            
//            log.info("Quote ["+orderQuote+"] Ask value:"+getAsk());
//            log.info("Quote ["+orderQuote+"] Bid value:"+getBid());
//            log.info("Quote ["+orderQuote+"] Change Close:"+getChgClose());
//            
//            log.info("Quote ["+orderQuote+"] Change Close Percentage:"+getChgClosePrcn());
//            
//            setAlreadyAddedChgClosePrcn(getChgClosePrcn());
//            
//    		setWildCardQuoteSleepTime();
//
//            if(isMarketResearch()){
//            	MarketResearch marketResearch= MarketResearch.getInstance();
//            	marketResearch.addQuote(orderQuote, getChgClosePrcn());
//            	
//            }else if(!isOrderAlreadyPlaced() && !isOrdersExceedDailyLimit() && !isQuoteInBlockerQueue() && !isOrderInprogress() && isGoodOrderPlacingTime()){
//            	
//                if((isWildCardQuote() && !isRegularSessionTime() &&  (this.chgClosePrcnLimit>0 && getReconcilableChgClosePrcn() >=this.chgClosePrcnLimit) || (this.chgClosePrcnLimit<0 && getReconcilableChgClosePrcn() <=this.chgClosePrcnLimit )) ||
//                (!isWildCardQuote() && (this.chgClosePrcnLimit>0 && getReconcilableChgClosePrcn() >=this.chgClosePrcnLimit) || (this.chgClosePrcnLimit<0 && getReconcilableChgClosePrcn() <=this.chgClosePrcnLimit ))){
//                	if(this.chgClosePrcnLimit>0 && getReconcilableChgClosePrcn() >=(this.chgClosePrcnLimit*3)/2 && counter<WAIT_COUNTER){
//                		log.info("Highly shooted up Quote ["+orderQuote+"]. Waiting to meet reasonable price limit percentage ["+this.chgClosePrcnLimit+"] for ["+(++counter)+"] times...");
//                	} else if(this.chgClosePrcnLimit<0 && getReconcilableChgClosePrcn() <=(this.chgClosePrcnLimit*3)/2 && counter<WAIT_COUNTER){
//                		log.info("Collapsing badly Quote ["+orderQuote+"]. Waiting to meet reasonable price limit percentage ["+this.chgClosePrcnLimit+"] for ["+(++counter)+"] times...");
//                	} else {
//                		placeOrder(false);
//                	}
//                }else{
//                	if(new Long(quoteSleepTime).equals(getWILD_CARD_QUOTE_SLEEP_TIME_INIT()) && isRegularSessionTime() && isQuoteTrending()){
//            			log.info("Trying to place order based on trending status of quote for "+ this.orderQuote+ " for quantity of "+this.quantity);
//            			placeOrder(false);
//                		
//                	} else{
//                    	log.info("Not ready to place Order for Quote ["+orderQuote+"]. Waiting to meet price limit percentage ["+this.chgClosePrcnLimit+"]...");
//                	}
//                }
//            } else {
//            	if(isOrderClosingRequiredConfig() && isWildCardQuote()){
//            		checkAndPlaceCloseOrder(true);	
//            	} else {
//            		log.info("Order closing configuration currently disabled. Please check the configuration.");
//            	}
//            	
//            	
//            }
//            
//            
//        }catch(UnknownHostException excp){    
//        	excp.printStackTrace();
//        	setTaskComplete(true);
//        } catch(Throwable excp){
//        	excp.printStackTrace();
//        	log.error("Error occured:"+excp.getMessage());
//        	log.error("Error Option Chains Respone:"+optionChainsResponse);
//        	
//        }
//        
//		if(isAfterHoursSessionCloseTime()){
//			setTaskComplete(true);
//		}
    }


	@Override
	protected String getTokenSecret() {
		return this.accessTokenSecret;
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


	public long getOptionChainsSleepTime() {
		return optionChainsSleepTime;
	}


	public void setOptionChainsSleepTime(long optionChainsSleepTime) {
		this.optionChainsSleepTime = optionChainsSleepTime;
	}	

}

