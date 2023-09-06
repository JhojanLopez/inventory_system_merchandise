package com.example.merchandise.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {
    private final WebClient webClient;
    @Value("${microservice.url-users}")
    private String url;

    @Override
    public boolean existUserById(long id) {
        try {
            webClient.get()
                    .uri(String.format("%s/api/v1/user/%s", url, id))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return true;

        } catch (WebClientResponseException.NotFound e) {
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
