import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class Species {
  private int id;
  private String name;
  private String classification;
  private String habitat;
  private boolean endangered;

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

}
