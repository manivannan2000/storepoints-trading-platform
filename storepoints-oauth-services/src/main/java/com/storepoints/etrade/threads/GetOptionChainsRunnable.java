package com.storepoints.etrade.threads;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.storepoints.etrade.api.ApiBase;
import com.storepoints.etrade.api.GetOptionChains;

public class GetOptionChainsRunnable  implements Runnable{
	
	 private static final Log log = LogFactory.getLog(GetOptionChainsRunnable.class);
	
	private GetOptionChains optionChains;
	
	
	public GetOptionChainsRunnable(String accessToken, String accessTokenSecret, String chainType, int expirationMonth, int expirationYear, String underlier) {
		optionChains=new GetOptionChains(accessToken, accessTokenSecret,  chainType, expirationMonth, expirationYear, underlier);
	}
	
	
	public void run() {
		
		while(ApiBase.isRegularSessionTime()){
			
			try {
				optionChains.optionChains();
				
				Thread.sleep(optionChains.getOptionChainsSleepTime());
				

			} catch (InvalidKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	

}
