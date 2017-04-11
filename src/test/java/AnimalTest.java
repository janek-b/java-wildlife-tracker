import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.text.DateFormat;

public class AnimalTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void animal_instantiatesCorrectly_false() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    assertEquals(true, testAnimal instanceof Animal);
  }

  @Test
  public void getSpeciesId_returnsSpeciesIdOfAnimal() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    assertEquals(1, testAnimal.getSpeciesId());
  }

  @Test
  public void getHealth_addMoreInformationToAnimal() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    assertEquals("OKAY", testAnimal.getHealth());
  }

  @Test
  public void getAge_addMoreInformationToAnimal() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    assertEquals("ADULT", testAnimal.getAge());
  }

  @Test
  public void getIdentifier_addMoreInformationToAnimal() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    assertEquals("Tag on ear", testAnimal.getIdentifier());
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Animal firstAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    Animal anotherAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    assertTrue(firstAnimal.equals(anotherAnimal));
  }


  @Test
  public void save_assignsIdToObjectAndSavesObjectToDatabase() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    Animal savedAnimal = Animal.all().get(0);
    assertEquals(testAnimal.getId(), savedAnimal.getId());
  }

  @Test
  public void all_returnsAllInstancesOfAnimal_false() {
    Animal firstAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    firstAnimal.save();
    Animal secondAnimal = new Animal(2, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    secondAnimal.save();
    assertEquals(true, Animal.all().get(0).equals(firstAnimal));
    assertEquals(true, Animal.all().get(1).equals(secondAnimal));
  }

  @Test
  public void find_returnsAnimalWithSameId_secondAnimal() {
    Animal firstAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    firstAnimal.save();
    Animal secondAnimal = new Animal(2, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    secondAnimal.save();
    assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
  }

  @Test
  public void delete_deletesAnimalFromDatabase_0() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    testAnimal.delete();
    assertEquals(0, Animal.all().size());
  }

  @Test
  public void updateName_updatesAnimalNameInDatabase_String() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    testAnimal.update(Animal.Health.SICK.toString(), Animal.Age.YOUNG.toString(), "Scar on leg");
    assertEquals("SICK", Animal.find(testAnimal.getId()).getHealth());
    assertEquals("YOUNG", Animal.find(testAnimal.getId()).getAge());
    assertEquals("Scar on leg", Animal.find(testAnimal.getId()).getIdentifier());
  }

  @Test
  public void addSighting_createsEntryInJoinTable() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", true);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting.save();
    Animal testAnimal = new Animal(testSpecies.getId(), Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    testAnimal.addSighting(testSighting);
    assertTrue(testAnimal.getSightings().contains(testSighting));
  }

  @Test
  public void getSightingCount_createsEntryInJoinTable() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", true);
    testSpecies.save();
    Sighting testSighting1 = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting (testSpecies.getId(), "45.472428, -121.946466", 2);
    testSighting2.save();
    Animal testAnimal = new Animal(testSpecies.getId(), Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    testAnimal.addSighting(testSighting1);
    testAnimal.addSighting(testSighting2);
    assertEquals((Integer) 2, testAnimal.getSightingCount());
  }

  @Test
  public void getEndangeredSightings() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", true);
    testSpecies.save();
    Sighting testSighting1 = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting (testSpecies.getId(), "45.472428, -121.946466", 2);
    testSighting2.save();
    Animal testAnimal = new Animal(testSpecies.getId(), Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    Animal testAnimal2 = new Animal(testSpecies.getId(), Animal.Health.SICK.toString(), Animal.Age.YOUNG.toString(), "Spot on leg");
    testAnimal2.save();
    testAnimal.addSighting(testSighting1);
    testAnimal2.addSighting(testSighting2);

    assertEquals("Deer", Animal.getEndangeredSightings().get(0).get("name"));
    assertEquals(DateFormat.getDateTimeInstance().format(Sighting.find(testSighting1.getId()).getTime()), DateFormat.getDateTimeInstance().format(Animal.getEndangeredSightings().get(0).get("time")));
    assertEquals("45.472428, -121.946466", Animal.getEndangeredSightings().get(0).get("location"));
    assertEquals("SICK", Animal.getEndangeredSightings().get(0).get("health"));
    assertEquals("YOUNG", Animal.getEndangeredSightings().get(0).get("age"));
    assertEquals("Spot on leg", Animal.getEndangeredSightings().get(0).get("identifier"));
    assertEquals("Deer", Animal.getEndangeredSightings().get(1).get("name"));
    assertEquals(DateFormat.getDateTimeInstance().format(Sighting.find(testSighting2.getId()).getTime()), DateFormat.getDateTimeInstance().format(Animal.getEndangeredSightings().get(1).get("time")));
    assertEquals("45.472428, -121.946466", Animal.getEndangeredSightings().get(1).get("location"));
    assertEquals("OKAY", Animal.getEndangeredSightings().get(1).get("health"));
    assertEquals("ADULT", Animal.getEndangeredSightings().get(1).get("age"));
    assertEquals("Tag on ear", Animal.getEndangeredSightings().get(1).get("identifier"));
  }

  @Test
  public void getLastSighted_returnsLastSightingOfAnimal() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", true);
    testSpecies.save();
    Sighting testSighting1 = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting1.save();
    Sighting testSighting2 = new Sighting (testSpecies.getId(), "45.472428, -121.946466", 2);
    testSighting2.save();
    Animal testAnimal = new Animal(testSpecies.getId(), Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    testAnimal.addSighting(testSighting1);
    testAnimal.addSighting(testSighting2);
    assertEquals(testSighting2, testAnimal.getLastSighted());
  }

  public void search_returnsMatchingResultsBasedOnIdentifier() {
    Species testSpecies = new Species("Wolverine", "Mammal", "Forest", true);
    testSpecies.save();
    Animal testAnimal1 = new Animal(testSpecies.getId(), Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal1.save();
    Animal testAnimal2 = new Animal(testSpecies.getId(), Animal.Health.SICK.toString(), Animal.Age.YOUNG.toString(), "Spot on ear");
    testAnimal2.save();
    Animal testAnimal3 = new Animal(testSpecies.getId(), Animal.Health.SICK.toString(), Animal.Age.YOUNG.toString(), "Spot on leg");
    testAnimal3.save();
    Animal[] animals = new Animal[] {testAnimal1, testAnimal2};
    assertTrue(Animal.search("ear").containsAll(Arrays.asList(animals)));
    assertFalse(Animal.search("ear").contains(testAnimal3));
  }

  // @Test
  // public void find_returnsNullWhenNoAnimalFound_null() {
  //   assertTrue(Animal.find(999) == null);
  // }

}
