import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

public class Sighting {
  private int speciesId;
  private String location;
  private int userId;
  private Timestamp time;
  private int id;
  private String image;

  public Sighting(int speciesId, String location, int userId) {
    this.speciesId = speciesId;
    this.location = location;
    this.userId = userId;
  }

  public int getId() {
    return this.id;
  }

  public Timestamp getTime() {
    return this.time;
  }

  public int getSpeciesId() {
    return this.speciesId;
  }

  public String getLocation() {
    return this.location;
  }

  public int getUserId() {
    return this.userId;
  }

  public String getUserName() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT name FROM users WHERE id = :userId;";
      return con.createQuery(sql)
        .addParameter("userId", this.userId)
        .executeAndFetchFirst(String.class);
    }
  }

  public String getSpeciesName() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT name FROM species WHERE id = :speciesId;";
      return con.createQuery(sql)
        .addParameter("speciesId", this.speciesId)
        .executeAndFetchFirst(String.class);
    }
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE sightings SET image = :image WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("image", image)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherSighting) {
    if(!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getSpeciesId() == newSighting.getSpeciesId() &&
             this.getLocation().equals(newSighting.getLocation()) &&
            //  DateFormat.getDateTimeInstance().format(this.getTime()).equals(DateFormat.getDateTimeInstance().format(newSighting.getTime())) &&
             this.getUserId() == newSighting.getUserId() &&
             this.getId() == newSighting.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO sightings (speciesId, location, userId, time) VALUES (:speciesId, :location, :userId, now());";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("speciesId", this.speciesId)
        .addParameter("location", this.location)
        .addParameter("userId", this.userId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Sighting> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings;";
      return con.createQuery(sql)
        .executeAndFetch(Sighting.class);
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE id=:id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
    }
    // catch (IndexOutOfBoundsException exception) {
    //   return null;
    // }
  }

  public static List<Sighting> getRecentSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE time > (CURRENT_DATE - INTERVAL '30 days') ORDER BY time desc;";
      return con.createQuery(sql)
        .executeAndFetch(Sighting.class);
    }
  }

}
