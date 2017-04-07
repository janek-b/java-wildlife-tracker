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
    this.id = id;
  }

  public int getId() {
    return this.id;
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

  // @Override
  // public boolean equals(Object otherSighting) {
  //   if(!(otherSighting instanceof Sighting)) {
  //     return false;
  //   } else {
  //     Sighting newSighting = (Sighting) otherSighting;
  //     return this.getAnimalId() == (newSighting.getAnimalId()) && this.getLocation().equals(newSighting.getLocation()) && this.getRangerName().equals(newSighting.getRangerName());
  //   }
  // }

  // public void save() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO sightings (animal_id, location, ranger_name) VALUES (:animal_id, :location, :ranger_name);";
  //     this.id = (int) con.createQuery(sql, true)
  //       .addParameter("animal_id", this.animal_id)
  //       .addParameter("location", this.location)
  //       .addParameter("ranger_name", this.ranger_name)
  //       .throwOnMappingFailure(false)
  //       .executeUpdate()
  //       .getKey();
  //   }
  // }
  //
  // public static List<Sighting> all() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM sightings;";
  //     return con.createQuery(sql)
  //       .throwOnMappingFailure(false)
  //       .executeAndFetch(Sighting.class);
  //   }
  // }
  //
  // public static Sighting find(int id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM sightings WHERE id=:id;";
  //     Sighting sighting = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(Sighting.class);
  //     return sighting;
  //   } catch (IndexOutOfBoundsException exception) {
  //     return null;
  //   }
  // }

}
