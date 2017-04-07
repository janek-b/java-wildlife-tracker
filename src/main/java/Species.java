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

}
