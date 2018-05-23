package fr.me.interfaces.ws;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fr.me.application.MoviesAndMarksFinder;
import fr.me.application.exception.HtmlFetcherException;
import fr.me.domain.CinemaTheater;
import fr.me.domain.Movie;
import fr.me.domain.MoviesSample;
import fr.me.interfaces.converters.allocine.CinemaConverter;
import fr.me.interfaces.converters.allocine.MovieConverter;

@Path("/movies")
public class MoviesService {

  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)
  public JSONObject getAllMovies() {

    Collection<Movie> moviesList = null;
    try {

      moviesList = MoviesAndMarksFinder.fetchAllInformations();

    } catch (HtmlFetcherException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    JSONObject jsonResultsList = new JSONObject();
    JSONArray movies = new JSONArray();
    for (Movie movie : moviesList) {

      movies.add(MovieConverter.convertMovieToJson(movie));
    }

    jsonResultsList.put("movies", movies);

    JSONObject jsonTheatersList = new JSONObject();
    JSONArray theaters = new JSONArray();
    for (CinemaTheater theater : CinemaTheater.getCinemasList().values()) {

      theaters.add(theater);
    }

    jsonResultsList.put("theaters", theaters);

    return jsonResultsList;
  }

  @GET
  @Path("/sample")
  @Produces(MediaType.APPLICATION_JSON)
  public JSONObject getSampleMovies() {

    Collection<Movie> moviesList = MoviesSample.getMovies();

    JSONObject jsonResultsList = new JSONObject();
    JSONArray movies = new JSONArray();
    for (Movie movie : moviesList) {

      movies.add(MovieConverter.convertMovieToJson(movie));
    }

    jsonResultsList.put("movies", movies);

    JSONObject jsonTheatersList = new JSONObject();
    JSONArray theaters = new JSONArray();
    for (CinemaTheater theater : CinemaTheater.getCinemasList().values()) {

      theaters.add(theater);
    }

    jsonResultsList.put("theaters", theaters);

    return jsonResultsList;
  }

}
