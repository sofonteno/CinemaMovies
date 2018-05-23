package fr.me.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Page {

  private static Map<Integer, Page> pagesList = new LinkedHashMap<>();

  private int pageNumber;
  private String url;

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String nextUrl) {
    this.url = nextUrl;
  }

  public static Map<Integer, Page> getPagesList() {
    return pagesList;
  }

}
