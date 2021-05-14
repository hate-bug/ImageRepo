package com.shopify.imagerepo.Security;

public class SecurityURLs {
    
    public static final String SIGN_UP_URLS = "/api/user/**"; // Allow user to register
    public static final String H2_Console = "/h2-console/**"; // Enable h2 database
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String Token_Prefix = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 600_000;  // 600 sec

}
