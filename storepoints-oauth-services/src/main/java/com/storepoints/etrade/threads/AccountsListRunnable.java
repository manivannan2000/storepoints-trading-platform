package com.storepoints.etrade.threads;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.storepoints.etrade.api.AccountsList;

public class AccountsListRunnable  implements Runnable{
	
	 private static final Log log = LogFactory.getLog(AccountsListRunnable.class);
	
	private AccountsList accountsList;
	
	
	public AccountsListRunnable(String accessToken, String accessTokenSecret){
		accountsList=new AccountsList(accessToken, accessTokenSecret);
	}


	public void run() {
		
		while(true){
			
			try {
				accountsList.accountsList();
				
				if(accountsList.getAccountId().startsWith("62"))
					log.info("HEARTBEAT OK");
				else
					log.info("HEARTBEAT failed. Please check.");
				
				Thread.sleep(120000);
				

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
