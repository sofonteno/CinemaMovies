package fr.me.interfaces;

import org.jsoup.select.Elements;
import org.junit.Test;

import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.Movie;
import fr.me.interfaces.converters.SensCritiqueConverter;

public class HtmlFetcherTest {

	private static final String URL_TEST = "https://www.senscritique.com/search?q=don%27t%20worry%20he%20won%27t%20get%20far%20on%20foot";

	private static final String SEARCHED_MOVIE_TAG_NAME = "div";
	private static final String SEARCHED_MOVIE_CLASS_NAME = "ProductSearchResult";

	@Test
	public void test() {
		Elements elements = null;

		Movie movie = new Movie();
		movie.setTitle("Avengers: Infinity War");
		try {
			elements = HtmlFetcher.getElementsByTagAndClassLike(SensCritiqueConverter.getScSearchUrlFromMovie(movie),
					SEARCHED_MOVIE_TAG_NAME, SEARCHED_MOVIE_CLASS_NAME);
			
//			elements = HtmlFetcher.getElementsByTagAndClassLike(URL_TEST,
//					SEARCHED_MOVIE_TAG_NAME, SEARCHED_MOVIE_CLASS_NAME);
			
			System.out.println("\nElements :\n");
			elements.forEach(e -> System.out.println("----------\n\r" + e));
			
		} catch (HtmlFetcherException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

}
