package fr.me.interfaces.converters.allocine;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.Page;
import fr.me.interfaces.HtmlFetcher;

public class PageConverter {
  private static final Logger LOGGER = LoggerFactory.getLogger(PageConverter.class);

  public static final String MOVIE_THEATERS_SEARCH_URL = "http://www.allocine.fr/salle/cinemas-pres-de-115755/?card=106001";

  private static final String ELEMENT_CLASS_NAME_1 = "morezonecontent";
  private static final String ELEMENT_CLASS_NAME_2 = "navcenterdata";

  private static final String NUMBER_PAGE_TAG_NAME = "li";

  private static final String URL_TAG_NAME = "a";
  private static final String URL_ATTR_NAME = "href";

  public static void fetchPages() throws HtmlFetcherException {
    LOGGER.info("Get pages...");

    Elements pages = HtmlFetcher.getElementsByClass(MOVIE_THEATERS_SEARCH_URL, ELEMENT_CLASS_NAME_1)
        .last().select(String.format(".%s", ELEMENT_CLASS_NAME_2));

    if (null != pages) {
      int i = 0;
      for (Element element : pages) {
        // if (++i < 2) {
        Page page = new Page();
        setPageNumberFromElement(element, page);
        setUrlFromElement(element, page);
        // }
      }
    }
  }

  private static void setPageNumberFromElement(Element element, Page page) {

    int pageNumber = Integer.valueOf(element.select(NUMBER_PAGE_TAG_NAME).text());
    page.setPageNumber(pageNumber);

    Page.getPagesList().put(pageNumber, page);
  }

  private static void setUrlFromElement(Element element, Page page) {

    String url = element.select(URL_TAG_NAME).attr(URL_ATTR_NAME);

    if (StringUtil.isBlank(url)) {
      url = String.format("%s/&page=1", MOVIE_THEATERS_SEARCH_URL);
    }
    page.setUrl(url);

  }
}
