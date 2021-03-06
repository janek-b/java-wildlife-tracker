import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DateFormat;
import java.util.Date;
import java.sql.Timestamp;

public class SightingTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void sighting_instantiatesCorrectly_true() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    assertEquals(true, testSighting instanceof Sighting);
  }

  @Test
  public void getSpeciesId_returnsSpeciesIdOfAnimalSighted() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    assertEquals(testSpecies.getId(), testSighting.getSpeciesId());
  }

  @Test
  public void getLocation_returnsTheLocationOfTheSighting() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    assertEquals("45.472428, -121.946466", testSighting.getLocation());
  }

  @Test
  public void getUserId_returnsUserIdOfTheSighting() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    assertEquals(1, testSighting.getUserId());
  }

  @Test
  public void getTime_returnsTimeOfSighting() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting.save();
    Timestamp savedSightingTime = Sighting.find(testSighting.getId()).getTime();
    Timestamp rightNow = new Timestamp(new Date().getTime());
    assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedSightingTime));
  }

  @Test
  public void equals_returnsTrueIfLocationAndDescriptionAreSame_true() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    Sighting anotherSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    assertTrue(testSighting.equals(anotherSighting));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Sighting() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(testSighting));
  }

  @Test
  public void all_returnsAllInstancesOfSighting_true() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting.save();
    Species secondSpecies = new Species("Black Bear", "Mammal", "Forest", false);
    secondSpecies.save();
    Sighting secondTestSighting = new Sighting (secondSpecies.getId(), "45.472428, -121.946466", 1);
    secondTestSighting.save();
    assertEquals(true, Sighting.all().get(0).equals(testSighting));
    assertEquals(true, Sighting.all().get(1).equals(secondTestSighting));
  }

  @Test
  public void find_returnsSightingWithSameId_secondSighting() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting.save();
    Species secondSpecies = new Species("Black Bear", "Mammal", "Forest", false);
    secondSpecies.save();
    Sighting secondTestSighting = new Sighting (secondSpecies.getId(), "45.472428, -121.946466", 1);
    secondTestSighting.save();
    assertEquals(Sighting.find(secondTestSighting.getId()), secondTestSighting);
  }

  @Test
  public void getRecentSightings_returnsListOfMostRecentSightings() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting1 = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting(testSpecies.getId(), "45.472428, -121.9", 2);
    testSighting2.save();
    Species secondSpecies = new Species("Black Bear", "Mammal", "Forest", false);
    secondSpecies.save();
    Sighting secondTestSighting = new Sighting (secondSpecies.getId(), "45.472428, -121.946466", 1);
    secondTestSighting.save();
    assertTrue(Sighting.getRecentSightings().get(0).equals(secondTestSighting));
    assertTrue(Sighting.getRecentSightings().get(1).equals(testSighting2));
    assertTrue(Sighting.getRecentSightings().get(2).equals(testSighting1));
  }


  @Test
  public void getImage_returnsImageUrl() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting.save();
    testSighting.setImage("imageURL");
    assertEquals("imageURL", testSighting.getImage());
    assertEquals("imageURL", Sighting.find(testSighting.getId()).getImage());
  }
  //
  // @Test
  // public void find_returnsNullWhenNoAnimalFound_null() {
  //   assertTrue(Animal.find(999) == null);
  // }

}
