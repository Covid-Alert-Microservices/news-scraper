package com.github.covidalert.newscraper;

import com.github.covidalert.newscraper.services.CovidTrackerFetcher;
import com.github.covidalert.newscraper.services.NewsDOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ScrapSchedule
{

    @Value("${microservice.news.url}")
    private String newsMicroservice;

    @Autowired
    private CovidTrackerFetcher covidTrackerFetcher;

    @Autowired
    private NewsDOMParser newsDOMParser;

    @Scheduled(cron = "0 0 * * * *")
    public void fetchArticlesFromCovidTracker()
    {
        var body = this.covidTrackerFetcher.fetchCovidTrackerHomePage();
        var news = body.map(this.newsDOMParser::getNewsFromCovidTrackerBody);
        news.flatMap(Mono::justOrEmpty).subscribe((articles ->
                WebClient
                        .create(String.format("%s/api/news", newsMicroservice))
                        .put()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(Mono.just(articles), List.class)
                        .retrieve()
                        .toBodilessEntity()
                        .doOnError(System.err::println)
                        .subscribe((_void) -> System.out.println("Successfully updated news"))));
    }

}
