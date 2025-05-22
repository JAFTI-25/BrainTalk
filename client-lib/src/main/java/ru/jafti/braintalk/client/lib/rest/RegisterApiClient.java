package ru.jafti.braintalk.client.lib.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import ru.jafti.braintalk.client.lib.settings.ConnectionSettings;
import ru.jafti.braintalk.server.api.RegisterApi;

@Component
public class RegisterApiClient implements RegisterApi {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ConnectionSettings connectionSettings;

    public RegisterApiClient(
            HttpClient httpClient,
            ObjectMapper objectMapper,
            ConnectionSettings connectionSettings
    ) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.connectionSettings = connectionSettings;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        try {
            String requestBody = objectMapper.writeValueAsString(request);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(connectionSettings.getServerUrl() + "/talker/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<RegisterResponse>() {});
            } else {
                throw new RuntimeException("Failed to register: " + response.statusCode() + " " + response.body());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error during register call", e);
        }
    }
}
