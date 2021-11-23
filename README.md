# News Scraper [![Build](https://github.com/Covid-Alert-Microservices/news-scraper/actions/workflows/build.yaml/badge.svg)](https://github.com/Covid-Alert-Microservices/news-scraper/actions/workflows/build.yaml)

This repository contains the source code for the microservice responsible for scraping the latest 
news from [CovidTracker](https://covidtracker.fr/).

## Development environment

Clone the repository and browser to project's folder:

`git clone git@github.com:Covid-Alert-Microservices/news-scraper.git && cd news-scraper`

Start the application:

`./gradlew bootRun` (or start it using your IDE)

The service microservice is available at http://localhost:8090 but doesn't expose any endpoint.

## Dependencies

This application is soft-dependent to [news-provider](https://github.com/Covid-Alert-Microservices/news-provider). You can start this service without news-provider but you won't be provided any befenit from it.
