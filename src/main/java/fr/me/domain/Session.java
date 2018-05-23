package fr.me.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.json.simple.JSONObject;

public class Session implements Comparable<Session> {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("E dd hh:MM");

	public Calendar date;
	public Movie movie;
	public CinemaTheater cinema;

	public Session() {
		super();
		this.date = new GregorianCalendar();

	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public CinemaTheater getCinema() {
		return cinema;
	}

	public void setCinema(CinemaTheater cinema) {
		this.cinema = cinema;
	}

	public String getDateToString() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
		df.setCalendar(this.date);
		dateFormat.setCalendar(this.date);
		return df.format(this.date.getTime());
	}

	public String getTimeToString() {
		DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.FRANCE);
		df.setCalendar(this.date);
		dateFormat.setCalendar(this.date);
		return df.format(this.date.getTime());
	}

	@Override
	public int compareTo(Session o) {
		return -date.compareTo(o.getDate());
	}

	@Override
	public String toString() {
		return "\rSession [date=" + getDateToString() + ", cinema=" + cinema + "]";
	}

}
