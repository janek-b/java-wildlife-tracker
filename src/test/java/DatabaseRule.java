import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteUserQuery = "DELETE FROM users *;";
      String deleteSpeciesQuery = "DELETE FROM species *;";
      String deleteAnimalsQuery = "DELETE FROM animals *;";
      String deleteSightingsQuery = "DELETE FROM sightings *;";
      String deleteAnimalsSightingsQuery = "DELETE FROM animals_sightings *;";
      con.createQuery(deleteAnimalsSightingsQuery).executeUpdate();
      con.createQuery(deleteUserQuery).executeUpdate();
      con.createQuery(deleteSpeciesQuery).executeUpdate();
      con.createQuery(deleteAnimalsQuery).executeUpdate();
      con.createQuery(deleteSightingsQuery).executeUpdate();
    }
  }


}
