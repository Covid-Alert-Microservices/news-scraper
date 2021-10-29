package com.github.covidalert.microservicetemplate;

import com.github.covidalert.microservicetemplate.pojos.Article;
import com.github.covidalert.microservicetemplate.services.NewsDOMParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NewsDOMParserTest
{

    private static final List<Article> expectedNews = new ArrayList<>(5);

    static
    {
        expectedNews.add(
                new Article(
                        "L’été Indien",
                        "En ce début d’automne la vague du variant delta (Indien) se dissipe en France, sans qu’on décèle de reprise importante consécutive à la rentrée scolaire et Universitaire pour l’instant. Est-ce le début de la fin de la pandémie, on ne peut hélas pas le prétendre avec une totale assurance même si les données incitent à l’optimisme.",
                        ZonedDateTime.parse("2021-10-18T10:38:45+02:00"),
                        "https://covidtracker.fr/lete-indien/"
                )
        );
        expectedNews.add(
                new Article(
                        "L’exploration continue",
                        "La descente épidémique reste bien engagée même si des particularités locales sont à noter. Le SARS-CoV-2 n’a pas livré tous ses secrets, alors l’étude continue avec les simulateurs.",
                        ZonedDateTime.parse("2021-09-27T17:57:22+02:00"),
                        "https://covidtracker.fr/lexploration-continue/"
                )
        );
        expectedNews.add(
                new Article(
                        "Ne pas réveiller le volcan qui dort…",
                        "La vague Delta est passée cet été sans atteindre (en métropole) les niveaux de saturation de l’hôpital que nous avions connus par le passé. L’immunité naturelle + vaccinale acquise suffira-t-elle à endiguer une ‘rechute du Delta cet hiver ?",
                        ZonedDateTime.parse("2021-09-13T22:34:32+02:00"),
                        "https://covidtracker.fr/ne-pas-reveiller-le-volcan-qui-dort/"
                )
        );
        expectedNews.add(
                new Article(
                        "Vive la rentrée?",
                        "D’un coté la vague de l’été décline ce qui rend de l’espoir, mais de l’autre il y a l’inconnue des effets de la rentrée scolaire et universitaire. On échappera difficilement à un rebond épidémique, la grande question étant de savoir quand et jusqu’où.",
                        ZonedDateTime.parse("2021-09-01T14:10:40+02:00"),
                        "https://covidtracker.fr/vive-la-rentree/"
                )
        );
        expectedNews.add(
                new Article(
                        "Reste à savoir si ça va freiner",
                        "La quatrième vague est enclenchée pour être plus longue qu’un épisode. La situation semble moins explosive que début juillet, mais sans doute pas encore assez contrée, pour le moment.",
                        ZonedDateTime.parse("2021-08-16T17:28:14+02:00"),
                        "https://covidtracker.fr/reste-a-savoir-si-ca-va-freiner/"
                )
        );
    }

    @Autowired
    private NewsDOMParser parser;

    private String readRawHtml()
    {
        Resource resource = new ClassPathResource("/webpage/covidtracker-homepage.htm");
        try
        {
            return Files.readString(Paths.get(resource.getURI()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new AssertionError("Unable to load raw html");
        }
    }

    @Test
    public void givenRawHtml_expectFiveNews()
    {
        String rawHtml = readRawHtml();

        var newsOpt = this.parser.getNewsFromCovidTrackerBody(rawHtml);
        assertThat(newsOpt).isNotEmpty();

        var news = newsOpt.get();
        assertThat(news).hasSize(5);

        assertThat(news).containsExactlyElementsOf(expectedNews);
    }

}
