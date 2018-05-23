package fr.me.application;

import org.junit.Test;

import fr.me.application.exception.HtmlFetcherException;

public class MoviesAndMarksFinderTest {

  @Test
  public void testFetchAllInformations() {

    try {

      MoviesAndMarksFinder.fetchAllInformations();

    } catch (HtmlFetcherException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
