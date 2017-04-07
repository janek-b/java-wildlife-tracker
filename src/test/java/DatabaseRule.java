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
      con.createQuery(deleteUserQuery).executeUpdate();
      String deleteSpeciesQuery = "DELETE FROM species *;";
      con.createQuery(deleteSpeciesQuery).executeUpdate();
      String deleteAnimalsQuery = "DELETE FROM animals *;";
      String deleteSightingsQuery = "DELETE FROM sightings *;";
      con.createQuery(deleteAnimalsQuery).executeUpdate();
      con.createQuery(deleteSightingsQuery).executeUpdate();
    }
  }


}
