package com.cvs.mailer.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppProperties {

    private String serverPort;
    private String serverHost;
    private String enableAuth;
    private String enableTLS;
    private String userName;
    private String password;

    public String getServerPort() {
        return serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getEnableAuth() {
        return enableAuth;
    }

    public String getEnableTLS() {
        return enableTLS;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setEnableAuth(String enableAuth) {
        this.enableAuth = enableAuth;
    }

    public void setEnableTLS(String enableTLS) {
        this.enableTLS = enableTLS;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
