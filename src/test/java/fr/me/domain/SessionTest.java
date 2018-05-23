package fr.me.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(SessionTest.class);

  @Test
  public void test() {
    List<Session> sessions = new ArrayList<>();

    Session session = new Session();
    CinemaTheater cinema = new CinemaTheater();
    cinema.setName("zerty");
    session.setCinema(cinema);

    String sd = "26-02-2018-09-30";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
    Calendar c = new GregorianCalendar();
    try {
      c.setTime(sdf.parse(sd));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    session.setDate(c);

    Session session2 = new Session();
    CinemaTheater cinema2 = new CinemaTheater();
    cinema.setName("zerty");
    session.setCinema(cinema2);

    String sd2 = "26-02-2018-09-30";
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
    Calendar c2 = new GregorianCalendar();
    try {
      c2.setTime(sdf2.parse(sd2));
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    session2.setDate(c2);

    sessions.add(session);
    sessions.add(session2);

    LOGGER.info(" {}", sessions);
  }

}
