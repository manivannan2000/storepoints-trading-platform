package com.storepoints.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsListAPIController {
	
    @RequestMapping("/accounts/rest/accountlist")
    public String accountsList() {
    	
    	String accountsListResponse=	"<AccountListResponse>"
    									+" 	<Account>"
    									+"	 	<accountDesc>MyAccount-1</accountDesc>"
    									+"	 	<accountId>83405188</accountId>"
    									+"		<marginLevel>MARGIN</marginLevel>"
    									+"		<netAccountValue>9999871.82</netAccountValue>"
    									+"		<registrationType>INDIVIDUAL</registrationType>"
    									+"  </Account>"
    									+"</AccountListResponse>";
    	
    	return accountsListResponse;

    }
    


}
