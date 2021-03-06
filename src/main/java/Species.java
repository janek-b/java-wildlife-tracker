import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Species implements DatabaseManagement {
  public enum AnimalGroups {
    MAMMALS,
    BIRDS,
    REPTILES,
    FISH,
    AMPHIBIANS,
    INSECTS,
    ARACHNIDS;
  }

  private int id;
  private String name;
  private String classification;
  private String habitat;
  private boolean endangered;
  private String image;

  public Species(String name, String classification, String habitat, boolean endangered) {
    this.name = name;
    this.classification = classification;
    this.habitat = habitat;
    this.endangered = endangered;
  }

  public String getName() {
    return this.name;
  }

  public String getClassification() {
    return this.classification;
  }

  public String getHabitat() {
    return this.habitat;
  }

  public boolean getEndangered() {
    return this.endangered;
  }

  public int getId() {
    return this.id;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE species SET image = :image WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("image", image)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherSpecies) {
    if (!(otherSpecies instanceof Species)) {
      return false;
    } else {
      Species newSpecies = (Species) otherSpecies;
      return this.getName().equals(newSpecies.getName()) &&
             this.getClassification().equals(newSpecies.getClassification()) &&
             this.getHabitat().equals(newSpecies.getHabitat()) &&
             this.getEndangered() == newSpecies.getEndangered() &&
             this.getId() == newSpecies.getId();
    }
  }

  @Override
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO species (name, classification, habitat, endangered) VALUES (:name, :classification, :habitat, :endangered);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("classification", this.classification)
        .addParameter("habitat", this.habitat)
        .addParameter("endangered", this.endangered)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Species> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM species ORDER BY name asc;";
      return con.createQuery(sql)
        .executeAndFetch(Species.class);
    }
  }

  public static Species find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM species WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Species.class);
    }
  }

  @Override
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM species WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void update(String name, String habitat, boolean endangered, String image) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE species SET (name, habitat, endangered, image) = (:name, :habitat, :endangered, :image) WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("habitat", habitat)
        .addParameter("endangered", endangered)
        .addParameter("image", image)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Animal> getAnimals() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE speciesId = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Animal.class);
    }
  }

  public List<Sighting> getSightings() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE speciesId = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

  public Integer getSightingCount() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT Count(id) FROM sightings WHERE speciesId = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeScalar(Integer.class);
    }
  }

  public static List<Species> getMostSighted() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM species AS a WHERE id IN (SELECT speciesId FROM sightings GROUP BY speciesId HAVING COUNT(*) > 0) ORDER BY (SELECT COUNT(*) FROM sightings WHERE speciesId = a.id) desc;";
      return con.createQuery(sql)
        .executeAndFetch(Species.class);
    }
  }

  public static List<Species> search(String input) {
    String newInput = "%" + input + "%";
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM species WHERE lower(name) LIKE lower(:newInput);";
      return con.createQuery(sql)
        .addParameter("newInput", newInput)
        .executeAndFetch(Species.class);
    }
  }

  public static List<Species> getEndangeredSpecies() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM species WHERE endangered = true ORDER BY name asc;";
      return con.createQuery(sql)
        .executeAndFetch(Species.class);
    }
  }

}
