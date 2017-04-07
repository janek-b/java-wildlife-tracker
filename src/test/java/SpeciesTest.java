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

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Species firstSpecies = new Species("Deer", "Mammal", "Forest", false);
    Species anotherSpecies = new Species("Deer", "Mammal", "Forest", false);
    assertTrue(firstSpecies.equals(anotherSpecies));
  }

  @Test
  public void save_assignsIdToObjectAndSavesObjectToDatabase() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Species savedSpecies = Species.all().get(0);
    assertEquals(testSpecies.getId(), savedSpecies.getId());
  }

  @Test
  public void all_returnsAllInstancesOfSpecies_false() {
    Species firstSpecies = new Species("Deer", "Mammal", "Forest", false);
    firstSpecies.save();
    Species secondSpecies = new Species("Black Bear", "Mammal", "Forest", false);
    secondSpecies.save();
    assertEquals(true, Species.all().get(0).equals(firstSpecies));
    assertEquals(true, Species.all().get(1).equals(secondSpecies));
  }

  @Test
  public void find_returnsSpeciesWithSameId_secondSpecies() {
    Species firstSpecies = new Species("Deer", "Mammal", "Forest", false);
    firstSpecies.save();
    Species secondSpecies = new Species("Black Bear", "Mammal", "Forest", false);
    secondSpecies.save();
    assertEquals(Species.find(secondSpecies.getId()), secondSpecies);
  }

}
