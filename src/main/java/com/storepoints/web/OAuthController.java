package com.storepoints.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {
	
    @RequestMapping("/oauth/request_token")
    public String index() {
        return "request_token response!";
    }


}
