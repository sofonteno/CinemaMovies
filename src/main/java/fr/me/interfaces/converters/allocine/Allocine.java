package fr.me.interfaces.converters.allocine;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.CinemaTheater;
import fr.me.domain.Page;
import fr.me.domain.Session;
import fr.me.interfaces.HtmlFetcher;

public class Allocine {
  private static final Logger LOGGER = LoggerFactory.getLogger(Allocine.class);

  public static final String BASE_URL = "http://www.allocine.fr";

  public static final String NAME = "Allocine";

  private static final String CINEMA_THEATER_CLASS_NAME = "theaterblock j_entity_container";

  private static final String BASE_MOVIES_1_CLASS = "showtimescore js_pane_wrapper";
  private static final String BASE_MOVIES_2_TAG = "p";

  private static final String SESSION_CLASS_NAME = "times";
  private static final String SESSION_TAG_NAME = "li";

  public static void fetchCinemaTheaters() throws HtmlFetcherException {

    for (Page page : Page.getPagesList().values()) {

      String url = PageConverter.MOVIE_THEATERS_SEARCH_URL;
      if (page.getPageNumber() > 1) {
        url = BASE_URL + page.getUrl();
      }

      Elements elements = HtmlFetcher.getElementsByClass(url, CINEMA_THEATER_CLASS_NAME);

      if (null != elements) {
        int i = 0;
        for (Element element : elements) {
          // if (++i < 2) {
          CinemaConverter.convertTheaterFromElement(element);

          // }
        }
      }
    }

  }

  public static void addMovieFromElement(Element element, CinemaTheater cinema)
      throws HtmlFetcherException {
    LOGGER.info("Get movies of cinema {}...", cinema.getName());
    Elements movies = element.getElementsByClass(BASE_MOVIES_1_CLASS).select(BASE_MOVIES_2_TAG);
    
    for (Element mElement : movies) {
      MovieConverter.convertMovieFromElement(mElement);

    }

    LOGGER.info("Get sessions of cinema {}...", cinema.getName());
    Elements sessions = element.select(String.format(".%s", SESSION_CLASS_NAME))
        .select(SESSION_TAG_NAME);

    for (Element sElement : sessions) {
      Session session = new Session();
      SessionConverter.addSessionFromElement(sElement, cinema, session);
    }
  }

}
