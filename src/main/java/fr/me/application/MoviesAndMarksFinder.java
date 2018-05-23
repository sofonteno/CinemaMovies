package fr.me.application;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.Movie;
import fr.me.interfaces.converters.SensCritiqueConverter;
import fr.me.interfaces.converters.allocine.Allocine;
import fr.me.interfaces.converters.allocine.PageConverter;

public class MoviesAndMarksFinder {
	private static final Logger LOGGER = LoggerFactory.getLogger(MoviesAndMarksFinder.class);

	public static Collection<Movie> fetchAllInformations() throws HtmlFetcherException {

		PageConverter.fetchPages();

		Allocine.fetchCinemaTheaters();

		SensCritiqueConverter.fetchMovies();

		LOGGER.debug("Title;Year;Cinema session;Day session;Hour session;SC mark;Allocine mark;Average mark");

		NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
		for (Movie m : Movie.getMoviesList().values()) {
			for (fr.me.domain.Session s : m.getSessions()) {

				Double scMark = m.getMarks().get(SensCritiqueConverter.NAME);
				String s_scMark = null;
				if (null != scMark) {
					try {
						s_scMark = nf.format(scMark);
					} catch (IllegalArgumentException e) {
					}
				}
				Double allMark = m.getMarks().get(Allocine.NAME);
				String s_allMark = null;
				if (null != allMark) {
					try {
						s_allMark = nf.format(allMark);
					} catch (IllegalArgumentException e) {
					}
				}
				BigDecimal averageMark = m.getAverageMark();
				String s_averageMark = null;
				if (null != averageMark) {
					try {
						s_averageMark = nf.format(averageMark);
					} catch (IllegalArgumentException e) {
					}
				}
				LOGGER.debug(String.format("%s;%s;%s;%s;%s;%s;%s;%s", m.getTitle(), m.getReleaseYear(),
						s.getCinema().getName(), s.getDateToString(), s.getTimeToString(), s_scMark, s_allMark,
						s_averageMark));
			}
		}

		return Movie.getMoviesList().values();
	}
}
