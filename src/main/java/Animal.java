import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Animal {
  public enum Health {
    ILL,
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

  // public void save() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO animals (name) VALUES (:name);";
  //     this.id = (int) con.createQuery(sql, true)
  //       .addParameter("name", this.name)
  //       .executeUpdate()
  //       .getKey();
  //   }
  // }
  //
  // public static List<Animal> all() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM animals;";
  //     return con.createQuery(sql)
  //       .executeAndFetch(Animal.class);
  //   }
  // }
  //
  // public static Animal find(int id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM animals WHERE id=:id;";
  //     Animal animal = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(Animal.class);
  //     return animal;
  //   }
  // }
  //
  // public void updateName(String name) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "UPDATE animals SET name=:name WHERE id=:id;";
  //     con.createQuery(sql)
  //       .addParameter("id", id)
  //       .addParameter("name", name)
  //       .executeUpdate();
  //   }
  // }
  //
  // public void delete() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "DELETE FROM animals WHERE id=:id;";
  //     con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }
  //
  // public List<Sighting> getSightings() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM sightings WHERE animal_id=:id;";
  //       List<Sighting> sightings = con.createQuery(sql)
  //         .addParameter("id", id)
  //         .executeAndFetch(Sighting.class);
  //     return sightings;
  //   }
  // }

}
