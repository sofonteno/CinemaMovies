package fr.me.domain;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import fr.me.interfaces.converters.SensCritiqueConverter;
import fr.me.interfaces.converters.allocine.Allocine;

public class MoviesSample {

  public static List<Movie> getMovies() {
    List<Movie> movies = new ArrayList<>();

    CinemaTheater luminor = new CinemaTheater();
    luminor.setName("Luminor Hôtel de Ville");
    luminor.setTheaterId(1);

    CinemaTheater villette = new CinemaTheater();
    villette.setName("Pathé la Villette");
    villette.setTheaterId(2);

    Movie movie = new Movie();
    movie.setTitle("Le Caire Confidentiel");
    movie.setReleaseYear(2017);

    Session session = new Session();
    session.setDate(new GregorianCalendar(2018, 3, 6, 15, 00));
    session.setCinema(villette);
    movie.addSession(session);

    session = new Session();
    session.setDate(new GregorianCalendar(2018, 3, 6, 21, 30));
    session.setCinema(luminor);
    movie.addSession(session);

    session = new Session();
    session.setDate(new GregorianCalendar(2018, 3, 5, 13, 30));
    session.setCinema(luminor);
    movie.addSession(session);

    movie.addMark(SensCritiqueConverter.NAME, 6.8);
    movie.addMark(Allocine.NAME, 7.8);

    movie.setUrlImage("resources/LeCaire.jpg");
    movie.setSynopsis(
        "Le Caire, janvier 2011, quelques jours avant le début de la révolution. Une jeune chanteuse est assassinée dans une chambre d’un des grands hôtels de la ville. Noureddine, inspecteur revêche chargé de l’enquête, réalise au fil de ses investigations que les coupables pourraient bien être liés à la garde rapprochée du président Moubarak."
            + "Le Caire, janvier 2011, quelques jours avant le début de la révolution. Une jeune chanteuse est assassinée dans une chambre d’un des grands hôtels de la ville. Noureddine, inspecteur revêche chargé de l’enquête, réalise au fil de ses investigations que les coupables pourraient bien être liés à la garde rapprochée du président Moubarak."
            + "Le Caire, janvier 2011, quelques jours avant le début de la révolution. Une jeune chanteuse est assassinée dans une chambre d’un des grands hôtels de la ville. Noureddine, inspecteur revêche chargé de l’enquête, réalise au fil de ses investigations que les coupables pourraient bien être liés à la garde rapprochée du président Moubarak."
            + "Le Caire, janvier 2011, quelques jours avant le début de la révolution. Une jeune chanteuse est assassinée dans une chambre d’un des grands hôtels de la ville. Noureddine, inspecteur revêche chargé de l’enquête, réalise au fil de ses investigations que les coupables pourraient bien être liés à la garde rapprochée du président Moubarak.");
    movie.setAllocineIdMovie("252157");

    movie.setGenre("Policier, Thriller");

    movie.addTheater(villette);
    movie.addTheater(luminor);

    movies.add(movie);

    movie = new Movie();
    movie.setTitle("La Juste route");
    movie.setReleaseYear(2017);

    session = new Session();
    session.setDate(new GregorianCalendar(2018, 3, 6, 13, 00));
    session.setCinema(luminor);
    movie.addSession(session);

    movie.addMark(SensCritiqueConverter.NAME, 5.5);
    movie.addMark(Allocine.NAME, 8.0);

    movie.setUrlImage("resources/LaJusteRoute.jpg");
    movie.setSynopsis(
        "En août 1945, au cœur de la Hongrie, un village s’apprête à célébrer le mariage du fils du notaire tandis que deux juifs orthodoxes arrivent, chargés de lourdes caisses. Un bruit circule qu’ils sont les héritiers de déportés et que d’autres, plus nombreux peuvent revenir réclamer leurs biens. Leur arrivée questionne la responsabilité de certains et bouleverse le destin des jeunes mariés. ");
    movie.setAllocineIdMovie("253643");

    movie.setGenre("Drame");

//    movie.addTheater(villette);
    movie.addTheater(luminor);

    movies.add(movie);

    CinemaTheater.getCinemasList().put(luminor.getTheaterId(), luminor);
    CinemaTheater.getCinemasList().put(villette.getTheaterId(), villette);

    return movies;
  }
}
