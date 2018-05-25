package com.storepoints.etrade.api;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.storepoints.etrade.model.EquityOrder;
import com.storepoints.etrade.threads.AccountsListRunnable;
import com.storepoints.etrade.threads.GetQuoteRunnable;

/**
 * a simple program to get request token and token secret.
 * 
 * 
 */
public class OAuthRequestToken  extends ApiBase {
	
	 private static final Log log = LogFactory.getLog(OAuthRequestToken.class);
	 
	 private  String requestToken;
	 private  String requestTokenSecret;
	 
	 private static final String HMAC_SHA1 = "HmacSHA1";

	 private static final String ENC = "UTF-8";

	 private static Base64 base64 = new Base64();
	 
	 

    
    
    public  void requestToken() throws ClientProtocolException,
    IOException, URISyntaxException, InvalidKeyException,
    NoSuchAlgorithmException {
    	
    	String requestTokenResponse=null;
        HttpClient httpclient = new DefaultHttpClient();
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        // These params should ordered in key
        qparams.add(new BasicNameValuePair("oauth_callback", "oob"));
        String consumerKey=getConsumerKey();
        qparams.add(new BasicNameValuePair("oauth_consumer_key", getConsumerKey()));
//        qparams.add(new BasicNameValuePair("oauth_consumer_key", key));
        qparams.add(new BasicNameValuePair("oauth_nonce", ""
                + (int) (Math.random() * 100000000)));
        qparams.add(new BasicNameValuePair("oauth_signature_method",
                "HMAC-SHA1"));
        qparams.add(new BasicNameValuePair("oauth_timestamp", ""
                + (System.currentTimeMillis() / 1000)));
        qparams.add(new BasicNameValuePair("oauth_version", "1.0"));

        // generate the oauth_signature
        String signature = getSignature("GET", URLEncoder.encode(
        		getServerHostProtocol()+"://"+getOAuthServerHostConfig()+"/oauth/request_token", ENC),
                URLEncoder.encode(URLEncodedUtils.format(qparams, ENC), ENC), true);

        // add it to params list
        qparams.add(new BasicNameValuePair("oauth_signature", signature));

        // generate URI which lead to access_token and token_secret.
        URI uri = URIUtils.createURI(getServerHostProtocol(), getOAuthServerHostConfig(), -1,
                "/oauth/request_token",
                URLEncodedUtils.format(qparams, ENC), null);

        log.info("Get Token and Token Secrect from:"
                + uri.toString());

        HttpGet httpget = new HttpGet(uri);
        // output the response content.
        log.info("Token and Token Secret:");
        

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            int len;
            byte[] tmp = new byte[2048];
            while ((len = instream.read(tmp)) != -1) {
            	requestTokenResponse=new String(tmp, 0, len, ENC);
            }
        }
        
        log.info(requestTokenResponse);
        
        setRequestToken(requestTokenResponse.substring(12,requestTokenResponse.indexOf("&")));
        
        setRequestTokenSecret(requestTokenResponse.substring(requestTokenResponse.indexOf("oauth_token_secret=")+19,requestTokenResponse.lastIndexOf("&")));
        
    }


	 public String getRequestToken() {
		return requestToken;
	}



	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}



	public String getRequestTokenSecret() {
		return requestTokenSecret;
	}



	public void setRequestTokenSecret(String requestTokenSecret) {
		this.requestTokenSecret = requestTokenSecret;
	}


	@Override
	protected String getTokenSecret() {
		return null;
	}

}
