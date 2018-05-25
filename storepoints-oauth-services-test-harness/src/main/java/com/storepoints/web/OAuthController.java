package com.storepoints.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {
	
    @RequestMapping("/oauth/request_token")
    public String requestToken() {
        return "oauth_token=%2FiQRgQCRGPo7Xdk6G8QDSEzX0Jsy6sKNcULcDavAGgU%3D&oauth_token_secret=%2FrC9scEpzcwSEMy4vE7nodSzPLqfRINnTNY4voczyFM%3D&oauth_callback_confirmed=true";
    }
    
    
    @RequestMapping("/oauth/access_token")
    public String accessToken() {
        return "oauth_token=%3TiQRgQCRGPo7Xdk6G8QDSEzX0Jsy6sKNcULcDavAGgU%3D&oauth_token_secret=%7RrC9scEpzcwSEMy4vE7nodSzPLqfRINnTNY4voczyFM%3D";
    }
    


}
