package com.dianping.rotate.admin.util;

import com.dianping.rotate.admin.model.UserProfile;
import org.jasig.cas.support.oauth.provider.impl.CasWrapperApi20;
import org.jasig.cas.support.oauth.provider.impl.OAuthEntity;
import org.scribe.model.OAuthConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class OAuthUtil {
    private String clientId;
    private String clientSecret;
    private String callbackUrl;
    private CasWrapperApi20 api = new CasWrapperApi20();


    OAuthConfig getOAuthConfig() {
        return new OAuthConfig(clientId, clientSecret, callbackUrl, null, null,
                null);
    }

    public String getAuthorizationUrl() {
        return api.getAuthorizationUrl(getOAuthConfig());
    }

    public UserProfile getUserProfile(String accessToken) {
        OAuthEntity entity = api.getProfile(accessToken);
        if(entity.getStatus() == OAuthEntity.STATUS_ERROR) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        String[] profile = entity.getProfile().split("\\|");
        UserProfile user = new UserProfile();
        user.setUserName(profile[0]);
        user.setLoginId(Integer.valueOf(profile[1]));
        user.setEmployeeNumber(profile[2]);
        user.setName(profile[3]);
        return user;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setServerUrl(String serverUrl) {
        CasWrapperApi20.setServerUrl(serverUrl);
    }

    void setApi(CasWrapperApi20 api) {
        this.api = api;
    }

}
