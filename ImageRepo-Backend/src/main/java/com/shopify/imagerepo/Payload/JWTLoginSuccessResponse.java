package com.shopify.imagerepo.Payload;

public class JWTLoginSuccessResponse {

    private boolean isSuccess;
    private String jwtToken;

    public JWTLoginSuccessResponse (boolean isSuccess, String jwtToken) {
       this.isSuccess = isSuccess;
       this.jwtToken = jwtToken;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String toString () {
        return "JWTLogInToken{" + "success=" + isSuccess + ", token='" + jwtToken + "\'" + "}";
    }
}
