package ru.jafti.braintalk.client.lib.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mizosoft.methanol.AdapterCodec;
import com.github.mizosoft.methanol.MediaType;
import com.github.mizosoft.methanol.Methanol;
import com.github.mizosoft.methanol.adapter.jackson.JacksonAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Methanol methanolClient(ConnectionSettings connectionSettings,
                                   ObjectMapper objectMapper) {

        var adapterCodec = AdapterCodec.newBuilder()
                .basic()
                .encoder(JacksonAdapterFactory.createEncoder(objectMapper, MediaType.APPLICATION_JSON))
                .decoder(JacksonAdapterFactory.createDecoder(objectMapper, MediaType.APPLICATION_JSON))
                .build();

        var httpClient = HttpClient.newHttpClient();

        var builder = Methanol.newBuilder(httpClient)
                .adapterCodec(adapterCodec)
                .baseUri(connectionSettings.getServerUrl())
                .requestTimeout(Duration.ofSeconds(20))
                .headersTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(5))
                .autoAcceptEncoding(true);

        return builder.build();
    }
}
