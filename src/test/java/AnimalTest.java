import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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

  public void updateName_updatesAnimalNameInDatabase_String() {
    Animal testAnimal = new Animal(1, Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    testAnimal.update(Animal.Health.ILL.toString(), Animal.Age.YOUNG.toString(), "Scar on leg");
    assertEquals("ILL", Animal.find(testAnimal.getId()).getHealth());
    assertEquals("YOUNG", Animal.find(testAnimal.getId()).getAge());
    assertEquals("Scar on leg", Animal.find(testAnimal.getId()).getIdentifier());
  }

  public void addSighting_createsEntryInJoinTable() {
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", 1);
    testSighting.save();
    Animal testAnimal = new Animal(testSpecies.getId(), Animal.Health.OKAY.toString(), Animal.Age.ADULT.toString(), "Tag on ear");
    testAnimal.save();
    testAnimal.addSighting(testSighting);
    assertTrue(testAnimal.getSightings().contains(testSighting));
  }

  // @Test
  // public void find_returnsNullWhenNoAnimalFound_null() {
  //   assertTrue(Animal.find(999) == null);
  // }

}
