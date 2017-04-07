import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class SpeciesTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void species_instantiatesCorrectly_false() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    assertEquals(true, testSpecies instanceof Species);
  }

  @Test
  public void getName_SpeciesInstantiatesWithName_Deer() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    assertEquals("Deer", testSpecies.getName());
  }

  @Test
  public void getClassification_SpeciesInstantiatesWithClassification_Mammal() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    assertEquals("Mammal", testSpecies.getClassification());
  }

  @Test
  public void getHabitat_SpeciesInstantiatesWithHabitat_Forest() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    assertEquals("Forest", testSpecies.getHabitat());
  }

  @Test
  public void getEndangered_SpeciesInstantiatesWithEndangered_false() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    assertEquals(false, testSpecies.getEndangered());
  }

}
