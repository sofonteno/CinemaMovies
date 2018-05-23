package fr.me.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import fr.me.application.exception.HtmlFetcherException;

public class HtmlFetcher {

	private static final int TIME_OUT = 100000;

	private static Document getBodyHtmlFromUrl(String sUrl) throws HtmlFetcherException {
		Authenticator authenticator = new Authenticator() {

			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("***", "***".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("***", 0));

		Document doc = null;
		try {

			URLConnection con = new URL(sUrl).openConnection(proxy);
			con.setConnectTimeout(TIME_OUT);
			con.setReadTimeout(TIME_OUT);
			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);

			String inputLine;
			String html = new String();
			while ((inputLine = in.readLine()) != null) {
				html += inputLine;
			}
			in.close();

			doc = Jsoup.parseBodyFragment(html);
		} catch (IOException e) {

		}

		return doc;

	}

	public static Elements getElementsByTag(String url, String tag) throws HtmlFetcherException {

		Document document = getBodyHtmlFromUrl(url);

		if (null != document) {

			return document.getElementsByTag(tag);

		} else {

			return null;

		}
	}

	public static Elements getElementsByClass(String url, String clazz) throws HtmlFetcherException {

		Document document = getBodyHtmlFromUrl(url);

		if (null != document) {

			return document.getElementsByClass(clazz);

		} else {

			return null;

		}
	}

	public static Elements getElementsByTagAndClass(String url, String tag, String clazz) throws HtmlFetcherException {

		Document document = getBodyHtmlFromUrl(url);

		if (null != document) {

			return document.select(String.format("%s.%s", tag, clazz));

		} else {

			return null;

		}
	}

	public static Elements getElementsByTagAndClassLike(String url, String tag, String clazz)
			throws HtmlFetcherException {

		Document document = getBodyHtmlFromUrl(url);

		if (null != document) {

			return document.select(String.format("%s[class^=%s]", tag, clazz));

		} else {

			return null;

		}
	}
}
