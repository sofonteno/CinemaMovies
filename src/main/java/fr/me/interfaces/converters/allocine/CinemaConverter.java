package fr.me.interfaces.converters.allocine;

import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.me.application.Util;
import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.CinemaTheater;

public class CinemaConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(CinemaConverter.class);

  private static final String THEATER_NAME_TAG_NAME_1 = "h2";
  private static final String THEATER_NAME_TAG_NAME_2 = "a";

  private static final String THEATER_ADDRESS_CLASS_NAME = "lighten";

  public static void convertTheaterFromElement(Element element) throws HtmlFetcherException {
    CinemaTheater cinema = new CinemaTheater();
    setNameFromElement(element, cinema);

    boolean getMovies = setAddressFromElement(element, cinema);
    if (getMovies) {
      Allocine.addMovieFromElement(element, cinema);

    }
  }

  private static void setNameFromElement(Element element, CinemaTheater cinema) {

    String name = element.select(THEATER_NAME_TAG_NAME_1).select(THEATER_NAME_TAG_NAME_2).text();
    cinema.setName(name);

  }

  private static boolean setAddressFromElement(Element element, CinemaTheater cinema) {

    String address = element.select(String.format(".%s", THEATER_ADDRESS_CLASS_NAME)).text();
    cinema.setAddress(address);

    int district = 0;

    try {

      district = Integer.valueOf(Util.getSubstringFromRegex(address, "\\d{5}"));
    } catch (NumberFormatException e) {

    }

    if (district >= 75000 && district < 76000) {

      cinema.setDistrict(district);

      CinemaTheater.getCinemasList().put(cinema.getTheaterId(), cinema);

      return true;
    } else {

      return false;
    }

  }

}
