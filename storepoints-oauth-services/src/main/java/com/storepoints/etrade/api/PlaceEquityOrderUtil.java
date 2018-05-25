package com.storepoints.etrade.api;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.storepoints.etrade.model.EquityOrder;
import com.storepoints.etrade.model.EquityOrderRequest;
import com.storepoints.etrade.model.PlaceEquityOrder;
import com.storepoints.exception.ApplicationException;

/**
 * a simple program to get Account List.
 * 
 * 
 */
public class PlaceEquityOrderUtil extends ApiBase {
	
	private static final Log log = LogFactory.getLog(PlaceEquityOrderUtil.class);
	
	private static final Object lock = new Object();	
	
	private  String requestXML= 		"<PlaceEquityOrder xmlns=\"http://order.etws.etrade.com\">"
										+		"<EquityOrderRequest>"
										+			"<accountId></accountId>"
										+			"<clientOrderId></clientOrderId>"	
										+			"<limitPrice></limitPrice>"
										+			"<previewId></previewId>"
										+			"<stopPrice></stopPrice>"
										+			"<allOrNone></allOrNone>"
										+			"<quantity></quantity>"
										+			"<reserveOrder></reserveOrder>"
										+			"<reserveQuantity></reserveQuantity>"
										+			"<symbol></symbol>"
										+			"<orderAction>BUY</orderAction>"
										+			"<priceType></priceType>"
										+			"<routingDestination></routingDestination>"
										+			"<marketSession>REGULAR</marketSession>"
										+			"<orderTerm>GOOD_UNTIL_CANCEL</orderTerm>"
										+		"</EquityOrderRequest>"
										+	"</PlaceEquityOrder>";
	

	private  String accessToken;
	private  String accessTokenSecret;
	
	private String serverHost;
	
	private String apiPath;
	
	private EquityOrder equityOrder;
    
    private int orderNum;
    
    public PlaceEquityOrderUtil(String accessToken,String accessTokenSecret, EquityOrder equityOrder) throws JsonProcessingException {
    	this.accessToken=accessToken;
    	this.accessTokenSecret=accessTokenSecret;
//    	this.serverHost="etwssandbox.etrade.com";
//    	this.apiPath="/accounts/sandbox/rest/accountlist";
    	this.serverHost=getServerHostConfig();
    	this.equityOrder=equityOrder;
    	this.apiPath="/order/rest/placeequityorder";
    	prepareRequestXML();
    	
    }
    
    private void prepareRequestXML() throws JsonProcessingException{
    	
    	PlaceEquityOrder placeEquityOrder = new PlaceEquityOrder();
    	
    	EquityOrderRequest equityOrderRequest= new EquityOrderRequest();
    	
    	equityOrderRequest.setAccountId(equityOrder.getAccountId());
    	equityOrderRequest.setSymbol(equityOrder.getSymbol());
    	equityOrderRequest.setOrderAction(equityOrder.getOrderAction());
    	equityOrderRequest.setClientOrderId(equityOrder.getClientOrderId());
    	equityOrderRequest.setPriceType(equityOrder.getPriceType());
    	
    	equityOrderRequest.setLimitPrice(equityOrder.getLimitPrice());
    	equityOrderRequest.setQuantity(equityOrder.getQuantity());
    	equityOrderRequest.setMarketSession(equityOrder.getMarketSession());
    	equityOrderRequest.setOrderTerm(equityOrder.getOrderTerm());
    	
    	placeEquityOrder.setEquityOrderRequest(equityOrderRequest);
    	
    	ObjectMapper xmlMapper = new XmlMapper();
    	setRequestXML(xmlMapper.writeValueAsString(placeEquityOrder));
    	
    }
    


    
    public  void orderEquity() throws ApplicationException, ClientProtocolException,
    IOException, URISyntaxException, InvalidKeyException,
    NoSuchAlgorithmException, StringIndexOutOfBoundsException {
    	
    	synchronized(lock){
    		
        	String placeEquityOrderResponse=null;
        	
            HttpClient httpclient = new DefaultHttpClient();
            List<NameValuePair> qparams = new ArrayList<NameValuePair>();
            // These params should ordered in key
//            qparams.add(new BasicNameValuePair("oauth_callback", "oob"));
            qparams.add(new BasicNameValuePair("oauth_consumer_key", getConsumerKey()));
            qparams.add(new BasicNameValuePair("oauth_nonce", ""
                    + (int) (Math.random() * 100000000)));
            qparams.add(new BasicNameValuePair("oauth_signature_method",
                    "HMAC-SHA1"));
            qparams.add(new BasicNameValuePair("oauth_timestamp", ""
                    + (System.currentTimeMillis() / 1000)));
//            qparams.add(new BasicNameValuePair("oauth_version", "1.0"));
            qparams.add(new BasicNameValuePair("oauth_token", getAccessToken()));
            
            // generate the oauth_signature
            String signature = getSignature("POST", URLEncoder.encode(
            		getServerHostProtocol()+"://"+getServerHost()+getApiPath(), ENC),
                    URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC), false);

            // add it to params list
            qparams.add(new BasicNameValuePair("oauth_signature", signature));

            // generate URI which lead to access_token and token_secret.
            URI uri = URIUtils.createURI(getServerHostProtocol(), getServerHost(), -1,
            		getApiPath(),
                    URLEncodedUtils.format(qparams, ENC), null);

            log.info("Place Equity Order from:"
                    + uri.toString());

            HttpPost httpPost = new HttpPost(uri);     
            httpPost.setHeader("Content-Type", "application/xml");
            
            
            requestXML=requestXML.replaceAll("xmlns=\"\"", "");
            log.info("Place Equity Order Request for ["+equityOrder+"]:\n"+requestXML);
            
            StringEntity xmlEntity = new StringEntity(requestXML);
            httpPost.setEntity(xmlEntity );
            

            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                int len;
                byte[] tmp = new byte[2048];
                while ((len = instream.read(tmp)) != -1) {
                    
                	placeEquityOrderResponse=new String(tmp, 0, len, ENC);
                    
                }
            }
            
            // output the response content.
            log.info("Place Equity Order Response["+equityOrder+"]:"+placeEquityOrderResponse);
            
            if(placeEquityOrderResponse.contains("Error")){
            	ObjectMapper xmlMapper = new XmlMapper();
            	
            	com.storepoints.etrade.model.Error errorApp= xmlMapper.readValue(placeEquityOrderResponse, com.storepoints.etrade.model.Error.class);
            	
            	throw new ApplicationException(errorApp.getErrorCode(), errorApp.getErrorMessage());
            	
            }
            
            
            setOrderNum(Integer.parseInt(placeEquityOrderResponse.substring(placeEquityOrderResponse.indexOf("<orderNum>")+10,placeEquityOrderResponse.indexOf("</orderNum>"))));
    	}
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

	public String getRequestXML() {
		return requestXML;
	}

	public void setRequestXML(String requestXML) {
		this.requestXML = requestXML;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	protected String getTokenSecret() {
		return this.accessTokenSecret;
	}

}
