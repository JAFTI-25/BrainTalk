package ru.jafti.braintalk.client.lib.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class HttpClientConfiguration {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HttpClient getHttpClient() {
        return HttpClient.newHttpClient();
    }

}
