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

  @Test
  public void delete_deletesSpeciesFromDatabase_0() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    testSpecies.delete();
    assertEquals(0, Species.all().size());
  }

  @Test
  public void update_updatesSpeciesPropertiesInDatabase_String() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    testSpecies.update("Buck", "Hills", true, "imageURL");
    assertEquals("Buck", Species.find(testSpecies.getId()).getName());
    assertEquals("Hills", Species.find(testSpecies.getId()).getHabitat());
    assertEquals(true, Species.find(testSpecies.getId()).getEndangered());
    assertEquals("imageURL", Species.find(testSpecies.getId()).getImage());
  }

  @Test
  public void getAnimals_getListOfAnimalsOfThisSpecies() {
    Species testSpecies = new Species("Wolverine", "Mammal", "Forest", true);
    testSpecies.save();
    Animal testAnimal1 = new Animal(testSpecies.getId(), Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal1.save();
    Animal testAnimal2 = new Animal(testSpecies.getId(), Animal.Health.SICK.toString(), Animal.Age.ADULT.toString(), "Scar on face");
    testAnimal2.save();
    Animal testAnimal3 = new Animal(2, Animal.Health.SICK.toString(), Animal.Age.ADULT.toString(), "Scar on face");
    testAnimal3.save();
    Animal[] animals = new Animal[] {testAnimal1, testAnimal2};
    assertTrue(testSpecies.getAnimals().containsAll(Arrays.asList(animals)));
    assertFalse(testSpecies.getAnimals().contains(testAnimal3));
  }

  @Test
  public void getSightings_getListOfAllSightings() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting1 = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting (testSpecies.getId(), "45.472428, -121.946466", 2);
    testSighting2.save();
    Sighting[] sightings = new Sighting[] {testSighting1, testSighting2};
    assertTrue(testSpecies.getSightings().containsAll(Arrays.asList(sightings)));
  }

  @Test
  public void getSightingCount_returnsTheNumberOfSighting() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting1 = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting (testSpecies.getId(), "45.472428, -121.946466", 2);
    testSighting2.save();
    assertEquals((Integer) 2, testSpecies.getSightingCount());
  }

  @Test
  public void getMostSighted_returnsOrderedListOfMostSightedSpecies() {
    Species testSpecies1 = new Species("Deer", "Mammal", "Forest", false);
    testSpecies1.save();
    Sighting testSighting1 = new Sighting(testSpecies1.getId(), "45.472428, -121.946466", 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting (testSpecies1.getId(), "45.472428, -121.946466", 2);
    testSighting2.save();
    Species testSpecies2 = new Species("Wolverine", "Mammal", "Forest", true);
    testSpecies2.save();
    Sighting testSighting3 = new Sighting (testSpecies2.getId(), "45.472428, -121.946466", 2);
    testSighting3.save();
    assertTrue(Species.getMostSighted().get(0).equals(testSpecies1));
    assertTrue(Species.getMostSighted().get(1).equals(testSpecies2));
  }

  public void getImage_returnsImageURLCorrectly() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    testSpecies.setImage("imageURL");
    assertEquals("imageURL", testSpecies.getImage());
    assertEquals("imageURL", Species.find(testSpecies.getId()).getImage());
  }

}
