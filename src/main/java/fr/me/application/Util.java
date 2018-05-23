package fr.me.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.me.application.exception.ParserJsonException;

public class Util {
  private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

  public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
    return map.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(/* Collections.reverseOrder() */)).collect(Collectors
            .toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

  public static String stripAccents(String s) {
    s = Normalizer.normalize(s, Normalizer.Form.NFD);
    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    s = s.replaceAll("ğ", "g");
    return s;
  }

  public static String getSubstringFromRegex(String string, String regex) {
    String substring = "";

    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(string);
    while (m.find()) {
      substring += m.group();
    }
    return substring;
  }

  /**
   * Méthode qui parse un fichier JSON en liste java.
   * 
   * @param nomFichierJson
   *          nom du fichier JSON à parser
   * @param typeClasse
   *          classe contenue dans la liste en soirtie
   * @return une liste du type de la classe spécifié
   * @throws FileNotFoundException
   *           si le fichier JSON est introuvable
   * @throws ParserJsonException
   *           s'il y a un problème de lecture du fichier ou de mappage en objet
   *           java, ou de fermeture du fichier
   * 
   */
  public static <T> List<T> parserJsonToList(String nomFichierJson, Class<T> typeClasse)
      throws FileNotFoundException, ParserJsonException {

    List<T> listeRetour = null;

    /*
     * Récupération du fichier JSON dans un inputStream
     */
    InputStream data = Util.class.getResourceAsStream(String.format("/%s", nomFichierJson));

    /*
     * Si le fichier n'a pas été trouvé, on lance une exception
     */
    if (data == null) {
      LOGGER.error("Le fichier {} est introuvable", nomFichierJson);
      throw new FileNotFoundException(
          String.format("Le fichier %s est introuvable", nomFichierJson));
    }

    try {

      ObjectMapper objectMapper = new ObjectMapper();

      JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, typeClasse);

      listeRetour = objectMapper.readValue(data, type);

    } catch (IOException e) {
      LOGGER.error("Impossible de parser le fichier {}.", nomFichierJson);
      throw new ParserJsonException(
          String.format("Impossible de parser le fichier %s.", nomFichierJson), e.getCause());

    } finally {
      if (null != data) {
        try {
          data.close();

        } catch (IOException e) {
          LOGGER.error("Erreur dans la fermeture du fichier {}.", nomFichierJson);
          throw new ParserJsonException(
              String.format("Erreur dans la fermeture du fichier %s.", nomFichierJson), e);

        }
      }

    }

    return listeRetour;
  }
}