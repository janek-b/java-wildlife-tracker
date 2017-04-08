import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Animal {
  public enum Health {
    SICK,
    OKAY,
    HEALTHY;
  }
  public enum Age {
    NEWBORN,
    YOUNG,
    ADULT;
  }

  private int id;
  private int speciesId;
  private String health;
  private String age;
  private String identifier;

  public Animal(int speciesId, String health, String age, String identifier) {
    this.speciesId = speciesId;
    this.health = health;
    this.age = age;
    this.identifier = identifier;
  }

  public int getSpeciesId() {
    return this.speciesId;
  }

  public int getId() {
    return this.id;
  }

  public String getHealth() {
    return this.health;
  }

  public String getAge() {
    return this.age;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  @Override
  public boolean equals(Object otherAnimal) {
    if(!(otherAnimal instanceof Animal)) {
      return false;
    } else {
      Animal newAnimal = (Animal) otherAnimal;
      return this.getSpeciesId() == newAnimal.getSpeciesId() &&
             this.getHealth().equals(newAnimal.getHealth()) &&
             this.getAge().equals(newAnimal.getAge()) &&
             this.getIdentifier().equals(newAnimal.getIdentifier()) &&
             this.getId() == newAnimal.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals (speciesId, health, age, identifier) VALUES (:speciesId, :health, :age, :identifier);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("speciesId", this.speciesId)
        .addParameter("health", this.health)
        .addParameter("age", this.age)
        .addParameter("identifier", this.identifier)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Animal> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals;";
      return con.createQuery(sql)
        .executeAndFetch(Animal.class);
    }
  }

  public static Animal find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM animals WHERE id=:id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Animal.class);
    }
  }

  public void update(String health, String age, String identifier) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE animals SET (health, age, identifier) = (:health, :age, :identifier) WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("health", health)
        .addParameter("age", age)
        .addParameter("identifier", identifier)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM animals WHERE id=:id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addSighting(Sighting sighting) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO animals_sightings (animalId, sightingId) VALUES (:animalId, :sightingId);";
      con.createQuery(sql)
        .addParameter("animalId", this.id)
        .addParameter("sightingId", sighting.getId())
        .executeUpdate();
    }
  }

  public List<Sighting> getSightings() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT sightings.* FROM sightings JOIN animals_sightings ON (sightings.id = animals_sightings.sightingId) JOIN animals ON (animals_sightings.animalId = animals.id) WHERE animals.id=:id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Sighting.class);
    }
  }

  public Integer getSightingCount() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT Count(id) FROM animals_sightings WHERE animalId = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeScalar(Integer.class);
    }
  }

}
