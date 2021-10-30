package com.github.covidalert.newscraper.services;

import com.github.covidalert.newscraper.pojos.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsDOMParser
{

    public Optional<List<Article>> getNewsFromCovidTrackerBody(String covidTrackerBody)
    {
        var doc = parseHtml(covidTrackerBody);
        var newsBlock = getNewsBlock(doc);
        return newsBlock.map(this::getArticles);
    }

    private List<Article> getArticles(Element element)
    {
        return element.getElementsByTag("li")
                .stream()
                .map(this::getArticle)
                .collect(Collectors.toList());
    }

    private Article getArticle(Element element)
    {
        return new Article(
                getArticleTitle(element),
                getArticleSummary(element),
                getArticleDate(element),
                getArticleLink(element)
        );
    }

    private Optional<Element> getNewsBlock(Document doc)
    {
        var elements = doc.body().getElementsByClass("wp-block-latest-posts__list");
        return Optional.ofNullable(elements.first());
    }

    private String getArticleTitle(Element element)
    {
        var titleTag = Objects.requireNonNull(element.getElementsByTag("a").first());
        return titleTag.text();
    }

    private String getArticleLink(Element element)
    {
        var titleTag = Objects.requireNonNull(
                element.getElementsByTag("a").first()
        );
        return titleTag.attr("href");
    }

    private String getArticleSummary(Element element)
    {
        var summaryTag = Objects.requireNonNull(
                element.getElementsByTag("div").last()
        );
        return summaryTag.text();
    }

    private ZonedDateTime getArticleDate(Element element)
    {
        var dateTag = Objects.requireNonNull(
                element.getElementsByTag("time").first()
        );
        String isoDate = dateTag.attr("datetime");
        return ZonedDateTime.parse(isoDate);
    }

    private Document parseHtml(String xmlString)
    {
        return Jsoup.parse(xmlString);
    }

}
