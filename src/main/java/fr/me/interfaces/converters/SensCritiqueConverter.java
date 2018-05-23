package fr.me.interfaces.converters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.me.application.Util;
import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.Movie;
import fr.me.interfaces.HtmlFetcher;

public class SensCritiqueConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(SensCritiqueConverter.class);

	public static final String NAME = "SensCritique";

	public static final String SEARCH_URL = "https://www.senscritique.com/search?categories[0][0]=Films&q=";

	private static final String SEARCHED_MOVIE_TAG_NAME = "div";
	private static final String SEARCHED_MOVIE_CLASS_NAME = "ProductSearchResult";

	private static final String DIRECTORS = "Film de ";
	private static final String TITLE_AND_YEAR_TAG_NAME = "h2";
	private static final String YEAR_TAG_NAME = "span";

	private static final String MARK_SELECTION = "RatingGlobal";

	public static void fetchMovies() throws HtmlFetcherException {

		int moviesNumber = Movie.getMoviesList().size();
		int i = 1;
		for (Movie movie : Movie.getMoviesList().values()) {

			LOGGER.info(String.format("Get Sens Critiques' marks of movie %s (%s sur %s)", movie.getTitle(), i,
					moviesNumber));

			Elements scMovies = HtmlFetcher.getElementsByTagAndClassLike(
					SensCritiqueConverter.getScSearchUrlFromMovie(movie), SEARCHED_MOVIE_TAG_NAME,
					SEARCHED_MOVIE_CLASS_NAME);

			if (null != scMovies) {
				SensCritiqueConverter.updateMarkFromElementsAndMovie(scMovies, movie);
			}

			i++;
		}
	}

	public static void updateMarkFromElementsAndMovie(Elements elements, Movie movie) {
		Double mark = null;

		Element elementSearched = null;

		for (Element element : elements) {
			String directors = element.select("span:contains(" + DIRECTORS + ")").text().split(" -")[0];
			String[] s_directors = directors.split("Film de ");
			if (s_directors.length > 1) {
				s_directors = s_directors[1].split(", ");
			}
			for (String director : s_directors) {
				if (!StringUtil.isBlank(director) && movie.getDirectors().contains(Util.stripAccents(director))) {
					elementSearched = element;
					break;
				}
			}
			if (null != elementSearched) {
				break;
			}
			// String sYear =
			// element.select(TITLE_AND_YEAR_TAG_NAME).select(YEAR_TAG_NAME).text();
			// int year = 0;
			//
			// try {
			// year = Integer.valueOf(Util.getSubstringFromRegex(sYear, "\\d{4}"));
			//
			// } catch (NumberFormatException e) {
			//
			// }
			//
			// if (year >= yearSearched - 1 && year <= yearSearched + 1) {
			// elementSearched = element;
			// break;
			// }

		}
		if (null != elementSearched) {
			try {
				Element eMark = elementSearched.selectFirst("div[class^=" + MARK_SELECTION + "]");
				if (null != eMark) {
					mark = Double.valueOf(eMark.selectFirst("h4").text());
				}
			} catch (NumberFormatException e) {

			}
			movie.addMark(NAME, mark);
		}
	}

	public static String getScSearchUrlFromMovie(Movie movie) {

		// String title = Util
		// .stripAccents(movie.getTitle().replaceAll(" –", "").replaceAll(" ",
		// "+").replaceAll(",", "+"));
		String title = "";
		try {
			title = URLEncoder.encode(movie.getTitle(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SensCritiqueConverter.SEARCH_URL + title;
	}

}
