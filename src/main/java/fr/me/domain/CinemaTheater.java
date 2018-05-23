package fr.me.domain;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Theater")
public class CinemaTheater {
  private static final Logger LOGGER = LoggerFactory.getLogger(CinemaTheater.class);

  private static Map<Integer, CinemaTheater> cinemasList = new HashMap<>();

  @DatabaseField(generatedId = true)
  private int theaterId;
  @DatabaseField
  private String name;
  @DatabaseField
  private String address;
  @DatabaseField
  private int district;

  public CinemaTheater() {

  }

  public int getTheaterId() {
    return theaterId;
  }

  public void setTheaterId(int theaterId) {
    this.theaterId = theaterId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getDistrict() {
    return district;
  }

  public void setDistrict(int district) {
    this.district = district;
  }

  public static Map<Integer, CinemaTheater> getCinemasList() {
    return cinemasList;
  }

  @Override
  public String toString() {
    return name;
  }

}
