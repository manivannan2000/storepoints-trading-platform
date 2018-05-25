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
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * a simple program to get Account List.
 * 
 * 
 */
public class AccountsList extends ApiBase {
	
	private static final Log log = LogFactory.getLog(AccountsList.class);

	private  String accessToken;
	private  String accessTokenSecret;
	
	private String accountId;
	
	private String serverHost;
	
	private String apiPath;
    
    
    public AccountsList(String accessToken,String accessTokenSecret) {
    	this.accessToken=accessToken;
    	this.accessTokenSecret=accessTokenSecret;
//    	this.serverHost="etwssandbox.etrade.com";
//    	this.apiPath="/accounts/sandbox/rest/accountlist";
    	this.serverHost=getServerHostConfig();
    	this.apiPath="/accounts/rest/accountlist";
    }
    


    
    
    public  void accountsList() throws ClientProtocolException,
    IOException, URISyntaxException, InvalidKeyException,
    NoSuchAlgorithmException {
    	
    	String accountListResponse=null;
    	
        HttpClient httpclient = new DefaultHttpClient();
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        // These params should ordered in key
//        qparams.add(new BasicNameValuePair("oauth_callback", "oob"));
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

        log.info("Get List Accounts from:"
                + uri.toString());

        HttpGet httpget = new HttpGet(uri);
        // output the response content.
        log.info("List Accounts Response:");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            int len;
            byte[] tmp = new byte[2048];
            while ((len = instream.read(tmp)) != -1) {
            	accountListResponse=new String(tmp, 0, len, ENC);
            }
        }
        
        log.info(accountListResponse);
        
        try{
        	setAccountId(accountListResponse.substring(accountListResponse.indexOf("<accountId>")+11,accountListResponse.indexOf("</accountId>")));
        } catch(Throwable excp){
        	excp.printStackTrace();
        	log.error("Error occured:"+excp.getMessage());
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


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}





	@Override
	protected String getTokenSecret() {
		return this.accessTokenSecret;
	}
}
