import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;

public class Sighting {
  private int speciesId;
  private String location;
  private int userId;
  private Timestamp time;
  private int id;

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

  @Override
  public boolean equals(Object otherSighting) {
    if(!(otherSighting instanceof Sighting)) {
      return false;
    } else {
      Sighting newSighting = (Sighting) otherSighting;
      return this.getSpeciesId() == newSighting.getSpeciesId() &&
             this.getLocation().equals(newSighting.getLocation()) &&
            //  this.getTime() == newSighting.getTime() &&
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

}
