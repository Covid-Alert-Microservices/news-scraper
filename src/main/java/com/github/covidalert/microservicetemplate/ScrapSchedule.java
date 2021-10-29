package com.github.covidalert.microservicetemplate;

import com.github.covidalert.microservicetemplate.services.CovidTrackerFetcher;
import com.github.covidalert.microservicetemplate.services.NewsDOMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScrapSchedule
{

    @Autowired
    private CovidTrackerFetcher covidTrackerFetcher;

    @Autowired
    private NewsDOMParser newsDOMParser;

    @Scheduled(cron = "0 */5 * * * *")
    public void fetchArticlesFromCovidTracker()
    {
        var body = this.covidTrackerFetcher.fetchCovidTrackerHomePage();
        var news = body.map(this.newsDOMParser::getNewsFromCovidTrackerBody);
        var result = news.block();
        System.out.println(result);
    }

}
