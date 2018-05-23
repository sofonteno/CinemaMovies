package fr.me.interfaces.converters.allocine;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.me.application.Util;
import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.Movie;
import fr.me.interfaces.HtmlFetcher;

public class MovieConverter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieConverter.class);

	private static final String MOVIE_CLASS_NAME = "movie";
	private static final String MOVIE_INFOS_CLASS_NAME = "card-movie-overview";

	private static final String DETAILS_INFOS_CLASS = "ovw-synopsis-info";
	private static final String PRODUCTION_YEAR_WHAT = "Année de production";

	public static void convertMovieFromElement(Element element) throws HtmlFetcherException {

		Movie movie = new Movie();
		boolean getInfos = setSheetUrlFromElement(element, movie);

		if (getInfos) {
			Element sheetElement = HtmlFetcher
					.getElementsByClass(Allocine.BASE_URL + movie.getAllocineSheetUrl(), MOVIE_CLASS_NAME).get(0);

			if (null != sheetElement) {
				setTitleFromElement(sheetElement, movie);
				setProductionYearFromElement(sheetElement, movie);

				if (!StringUtil.isBlank(movie.getTitle())) {
					Element subElement = sheetElement.selectFirst("." + MOVIE_INFOS_CLASS_NAME);

					setSynopsisFromElement(sheetElement, movie);

					setMetasFromElement(subElement.selectFirst(".meta-body"), movie);
					setUrlImageFromElement(subElement, movie);

					setMarkFromElement(subElement, movie);
				}
			}
		}
	}

	private static boolean setSheetUrlFromElement(Element element, Movie movie) {

		String url = element.select("p").select("a").attr("href");
		movie.setAllocineSheetUrl(url);

		String urls[] = url.split("=");
		String id = urls[1].replace(".html", "");

		movie.setAllocineIdMovie(id);

		Movie existingMovie = Movie.getMoviesList().get(id);

		if (null == existingMovie) {

			Movie.getMoviesList().put(id, movie);
			return true;

		} else {

			return false;
		}

	}

	private static void setUrlImageFromElement(Element element, Movie movie) {

		movie.setUrlImage(element.select("figure").select("img").attr("src"));
	}

	private static void setTitleFromElement(Element element, Movie movie) {
		movie.setTitle(element.selectFirst(".titlebar-title").text());
	}

	private static void setProductionYearFromElement(Element element, Movie movie) {
		Integer productionYear = null;

		Element productionYearWhat = element.selectFirst("." + DETAILS_INFOS_CLASS)
				.selectFirst(".what:contains(" + PRODUCTION_YEAR_WHAT + ")");
		if (null != productionYearWhat) {
			String productionYearThat = productionYearWhat.siblingElements().select(".that").text();
			productionYear = Integer.valueOf(Util.getSubstringFromRegex(productionYearThat, "\\d{4}"));
		}

		movie.setReleaseYear(productionYear);
	}

	private static void setMarkFromElement(Element element, Movie movie) {
		Elements marks = element.select(".rating-item-content");
		Element pressElement = null;
		for (Element mark : marks) {
			String ratingTitle = mark.selectFirst(".rating-title").text();
			if (ratingTitle.equals("Presse")) {
				pressElement = mark;
				break;
			}
		}

		Double mark = null;
		if (null != pressElement) {
			mark = Double.valueOf(pressElement.select(".stareval-note").text().trim().replace(",", ".")) * 2;
		}

		movie.addMark(Allocine.NAME, mark);
	}

	private static void setMetasFromElement(Element element, Movie movie) {

		Elements metaItems = element.select(".meta-body-item");

		/*
		 * Release year
		 */
		Element releaseDateWhat = metaItems.select(".light:contains(Date de sortie)").first();
		String releaseDateThat = null;

		if (null != releaseDateWhat) {
			releaseDateThat = releaseDateWhat.siblingElements().select(".date").text();

			int releaseYear = 0;
			try {
				releaseYear = Integer.valueOf(Util.getSubstringFromRegex(releaseDateThat, "\\d{4}"));
			} catch (NumberFormatException e) {
			}
			movie.setReleaseYear(releaseYear);
		}

		/*
		 * Director
		 */
		List<String> movieDirectors = movie.getDirectors();
		Elements directors = metaItems.select("[itemprop='director']");
		for (Element director : directors) {
			movieDirectors.add(Util.stripAccents(director.selectFirst("a").attr("title")));
		}

		/*
		 * Genres
		 */
		Elements genreElements = metaItems.select("[itemprop='genre']");
		String genres = "";
		for (Element e : genreElements) {
			genres += e.text();
			if (e.siblingIndex() < genreElements.size() - 1) {
				genres.concat(", ");
			}
		}

	}

	private static void setSynopsisFromElement(Element element, Movie movie) {

		movie.setSynopsis(element.select(".synopsis-txt").text());
	}

	public static JSONObject convertMovieToJson(Movie movie) {

		Map<String, Object> mapInfos = new TreeMap<>();

		mapInfos.put("title", movie.getTitle());
		mapInfos.put("year", movie.getReleaseYear());

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);
		String averageMark = "-";

		try {
			averageMark = df.format(movie.getAverageMark());
		} catch (IllegalArgumentException e) {
			LOGGER.error("Impossible de formater la note {}", movie.getAverageMark());
		}

		mapInfos.put("averageMark", averageMark);

		JSONArray sessionsArray = SessionConverter.convertSessionsToJson(movie.getSessions());
		mapInfos.put("daySessions", sessionsArray);

//		JSONArray marksArray = convertMovieMarksToJson(movie);
		mapInfos.put("marks", convertMovieMarksToJson(movie));

		mapInfos.put("imageUrl", movie.getUrlImage());

		mapInfos.put("synopsis", movie.getSynopsis());

		mapInfos.put("id", movie.getAllocineIdMovie());

		mapInfos.put("genre", movie.getGenre());

		mapInfos.put("theaters", movie.getTheaters());

		JSONObject jsonMovieInfos = new JSONObject(mapInfos);

		JSONObject jsonMovie = new JSONObject();
		jsonMovie.put("movie", jsonMovieInfos);

		return jsonMovieInfos;
	}

	private static JSONObject convertMovieMarksToJson(Movie movie) {
		JSONObject marksObject = new JSONObject();

		for (String markSite : movie.getMarks().keySet()) {

			marksObject.put(markSite, movie.getMarks().get(markSite));

		}
		return marksObject;
	}
}
