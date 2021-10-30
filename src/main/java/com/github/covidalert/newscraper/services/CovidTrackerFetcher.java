package com.github.covidalert.newscraper.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CovidTrackerFetcher
{


    public Mono<String> fetchCovidTrackerHomePage()
    {
        WebClient webClient = WebClient.create("https://covidtracker.fr/");
        var response = webClient.get().retrieve();
        return response.bodyToMono(String.class);
    }

}
