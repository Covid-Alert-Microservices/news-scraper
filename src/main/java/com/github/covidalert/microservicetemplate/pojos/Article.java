package com.github.covidalert.microservicetemplate.pojos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Article
{

    private final String title;
    private final String summary;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private final ZonedDateTime date;
    private final String link;

    public Article(String title, String summary, ZonedDateTime date, String link)
    {
        this.title = title;
        this.summary = summary;
        this.date = date;
        this.link = link;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSummary()
    {
        return summary;
    }

    public ZonedDateTime getDate()
    {
        return date;
    }

    public String getLink()
    {
        return link;
    }

    @Override
    public String toString()
    {
        return "Article{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", date=" + date +
                ", link='" + link + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Article article = (Article) o;
        return title.equals(article.title) && summary.equals(article.summary) && date.equals(article.date) && link.equals(article.link);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(title, summary, date, link);
    }
}
