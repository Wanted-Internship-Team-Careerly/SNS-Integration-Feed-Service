package com.snsIntegrationFeedService.common.config;

import org.springframework.beans.factory.annotation.Value;

import io.netty.channel.ChannelOption;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

    @Value("${webclient.connect.timeout:100}")
    private int connectTimeout;

    @Value("${webclient.response.timeout:200}")
    private int responseTimeout;

    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
            .responseTimeout(Duration.ofMillis(responseTimeout));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder().clientConnector(connector);
    }
}
