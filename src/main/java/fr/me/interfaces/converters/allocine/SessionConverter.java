package fr.me.interfaces.converters.allocine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;

import fr.me.domain.CinemaTheater;
import fr.me.domain.Movie;
import fr.me.domain.Session;

public class SessionConverter {

  private static final Calendar TODAY = new GregorianCalendar();

  public static void addSessionFromElement(Element element, CinemaTheater cinema, Session session) {

    String s_date = element.select("em").attr("data-datetime");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    Calendar date = session.getDate();
    try {
      date.setTime(sdf.parse(s_date));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (date.compareTo(TODAY) > 0) {
      String movieId = element.select("em").attr("cmovie");
      Movie movie = Movie.getMoviesList().get(movieId);

      movie.addSession(session);
      session.setDate(date);
      session.setCinema(cinema);
      session.setMovie(movie);
      
      movie.addTheater(cinema);
    }
  }

  public static JSONArray convertSessionsToJson(Set<Session> sessions) {
    SessionConverter sessionConverter = new SessionConverter();

    SimpleDateFormat dayFormat = new SimpleDateFormat("E dd");
    SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

    Map<String, Set<SessionDay>> days = new HashMap<>();

    for (Session session : sessions) {

      String day = dayFormat.format(session.getDate().getTime());

      Set<SessionDay> hoursAndTheaters = days.get(day);
      if (null == hoursAndTheaters) {
        hoursAndTheaters = new HashSet<>();
        days.put(day, hoursAndTheaters);
      }

      SessionDay sessionDay = sessionConverter.new SessionDay();
      sessionDay.setHour(hourFormat.format(session.getDate().getTime()));
      sessionDay.setTheater(session.getCinema().getName());
      hoursAndTheaters.add(sessionDay);
    }

    JSONArray sessionsDayArray = new JSONArray();
    for (String day : days.keySet()) {
      JSONObject jsonDay = new JSONObject();
      jsonDay.put("day", day);

      JSONArray jsonArrayHoursAndTheaters = new JSONArray();
      for (SessionDay sessionDay : days.get(day)) {
        JSONObject jsonHoursAndTheaters = new JSONObject();
        jsonHoursAndTheaters.put("hour", sessionDay.getHour());
        jsonHoursAndTheaters.put("theater", sessionDay.getTheater());
        jsonArrayHoursAndTheaters.add(jsonHoursAndTheaters);
      }
      jsonDay.put("hourSessions", jsonArrayHoursAndTheaters);
      sessionsDayArray.add(jsonDay);
    }

    return sessionsDayArray;
  }

  public static JSONObject convertSessionToJson(Session session) {

    JSONObject jsonSession = new JSONObject();

    jsonSession.put("date", session.getDateToString());
    jsonSession.put("theater", session.getCinema().getName());

    return jsonSession;
  }

  class SessionDay {
    private String hour;
    private String theater;

    public SessionDay() {
      super();
    }

    public String getHour() {
      return hour;
    }

    public void setHour(String hour) {
      this.hour = hour;
    }

    public String getTheater() {
      return theater;
    }

    public void setTheater(String theater) {
      this.theater = theater;
    }

  }
}
