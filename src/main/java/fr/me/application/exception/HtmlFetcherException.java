package fr.me.application.exception;

public class HtmlFetcherException extends Throwable {

  public HtmlFetcherException(Throwable cause) {
    super(cause);
  }

  public HtmlFetcherException(String message, Throwable cause) {
    super(message, cause);
  }
}
