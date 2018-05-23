package fr.me.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Movie implements Comparable<Movie> {

	private static Map<String, Movie> moviesList = new HashMap<>();

	private String title;
	private Integer releaseYear;
	private Set<Session> sessions;
	private Map<String, Double> marks;
	private BigDecimal averageMark;

	private String allocineIdMovie;
	private String allocineSheetUrl;

	private String urlImage;
	private List<String> directors;
	private String genre;
	private String synopsis;

	private Set<CinemaTheater> theaters;

	public Movie() {
		super();
		marks = new HashMap<>();
		sessions = new TreeSet<>();
		theaters = new HashSet<>();
		directors = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public static Map<String, Movie> getMoviesList() {
		return moviesList;
	}

	public void addSession(Session session) {
		sessions.add(session);

	}

	public Set<Session> getSessions() {
		return sessions;
	}

	public BigDecimal getAverageMark() {
		return averageMark;
	}

	public void addMark(String siteName, Double mark) {
		marks.put(siteName, mark);
		updateAverageMark();
	}

	public Map<String, Double> getMarks() {
		return marks;
	}

	private void updateAverageMark() {
		this.averageMark = null;

		if (!marks.isEmpty()) {
			long size = marks.values().stream().filter(n -> null != n).mapToDouble(n -> n.doubleValue()).count();
			Double sum = marks.values().stream().filter(n -> null != n).mapToDouble(n -> n.doubleValue()).sum();

			if (size > 0) {
				this.averageMark = new BigDecimal(sum).divide(new BigDecimal(size), 1, RoundingMode.HALF_UP);
			}
		}
	}

	public String getAllocineIdMovie() {
		return allocineIdMovie;
	}

	public void setAllocineIdMovie(String allocineIdMovie) {
		this.allocineIdMovie = allocineIdMovie;
	}

	public String getAllocineSheetUrl() {
		return allocineSheetUrl;
	}

	public void setAllocineSheetUrl(String allocineSheetUrl) {
		this.allocineSheetUrl = allocineSheetUrl;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public List<String> getDirectors() {
		return directors;
	}

	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Set<CinemaTheater> getTheaters() {
		return theaters;
	}

	public void addTheater(CinemaTheater theater) {
		this.theaters.add(theater);
	}

	@Override
	public String toString() {

		return "Movie : " + title + " (" + releaseYear + "), Note moyenne = " + averageMark + " \r Seances :"
				+ sessions;

	}

	@Override
	public int compareTo(Movie o) {
		if (null == averageMark) {
			return 1;
		} else {
			if (null == o.getAverageMark()) {
				return -1;
			} else {
				return -averageMark.compareTo(o.getAverageMark());
			}
		}
	}

}
