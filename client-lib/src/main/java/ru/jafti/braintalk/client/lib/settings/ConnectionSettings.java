package ru.jafti.braintalk.client.lib.settings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionSettings {

    private final String prodServerUrl;
    private final String localServerUrl;
    private final boolean useLocalServer;

    public ConnectionSettings(
            @Value("${server.rest.url.prod}") String prodServerUrl,
            @Value("${server.rest.url.local}") String localServerUrl,
            @Value("${server.use.local}") Boolean serverUseLocal) {
        this.prodServerUrl = prodServerUrl;
        this.localServerUrl = localServerUrl;
        this.useLocalServer = serverUseLocal;

    }

    public String getProdServerUrl() {
        return prodServerUrl;
    }

    public String getLocalServerUrl() {
        return localServerUrl;
    }

    public String getServerUrl() {
        if (useLocalServer) {
            return getLocalServerUrl();
        }
        return getProdServerUrl();
    }

    @Override
    public String toString() {
        return "ConnectionSettings{" +
                "prodServerUrl='" + prodServerUrl + '\'' +
                ", localServerUrl='" + localServerUrl + '\'' +
                ", useLocalServer=" + useLocalServer +
                '}';
    }
}
