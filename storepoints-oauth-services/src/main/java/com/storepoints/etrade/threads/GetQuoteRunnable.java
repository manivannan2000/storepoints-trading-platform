package com.storepoints.etrade.threads;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.storepoints.etrade.api.AccountsList;
import com.storepoints.etrade.api.GetQuote;
import com.storepoints.etrade.dto.GetQuoteParams;

public class GetQuoteRunnable  implements Runnable{
	
	 private static final Log log = LogFactory.getLog(GetQuoteRunnable.class);
	
	private GetQuote getQuote;
	
	public GetQuoteRunnable(String accessToken, String accessTokenSecret, String accountId, String quotesList, String detailFlag,  int quantity, double chgClosePrcnLimit, boolean wildCardQuote) {
		getQuote=new GetQuote(accessToken, accessTokenSecret, accountId, quotesList, detailFlag, quantity, chgClosePrcnLimit, wildCardQuote);
	}
	
	
	public GetQuoteRunnable(GetQuoteParams quoteParams) {
		getQuote=new GetQuote(quoteParams);
	}
	


	public void run() {
		
		while(!getQuote.isTaskComplete()){
			
			try {
				getQuote.quote();
				
				Thread.sleep(getQuote.getQuoteSleepTime());
//				}else{
//					Thread.sleep(30000);	
//				}
					
				
				

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
