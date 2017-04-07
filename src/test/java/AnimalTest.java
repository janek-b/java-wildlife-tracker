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
    Animal testAnimal = new Animal(1);
    assertEquals(true, testAnimal instanceof Animal);
  }

  @Test
  public void getSpeciesId_returnsSpeciesIdOfAnimal() {
    Animal testAnimal = new Animal(1);
    assertEquals(1, testAnimal.getSpeciesId());
  }

  @Test
  public void addDetail_addMoreInformationToAnimal() {
    Animal testAnimal = new Animal(1);
    testAnimal.addDetail(Animal.Health.OKAY, Animal.Age.ADULT, "Tag on ear");
    assertEquals("OKAY", testAnimal.getHealth());
    assertEquals("ADULT", testAnimal.getAge());
    assertEquals("Tag on ear", testAnimal.getIdentifier());
  }

  @Test
  public void enumHealth_returnPossibleHealthValues() {
    Animal testAnimal = new Animal(1);
    for (Animal.Health health: Animal.Health.values()) {
      System.out.println(health);
    }
    // System.out.println(Animal.Health.values());
  }

  // @Test
  // public void getName_animalInstantiatesWithName_Deer() {
  //   Animal testAnimal = new Animal(1);
  //   assertEquals(1, testAnimal.getName());
  // }
  //
  // @Test
  // public void equals_returnsTrueIfNameIsTheSame_false() {
  //   Animal firstAnimal = new Animal(1);
  //   Animal anotherAnimal = new Animal(1);
  //   assertTrue(firstAnimal.equals(anotherAnimal));
  // }
  //
  // @Test
  // public void save_assignsIdToObjectAndSavesObjectToDatabase() {
  //   Animal testAnimal = new Animal(1);
  //   testAnimal.save();
  //   Animal savedAnimal = Animal.all().get(0);
  //   assertEquals(testAnimal.getId(), savedAnimal.getId());
  // }
  //
  // @Test
  // public void all_returnsAllInstancesOfAnimal_false() {
  //   Animal firstAnimal = new Animal(1);
  //   firstAnimal.save();
  //   Animal secondAnimal = new Animal(2);
  //   secondAnimal.save();
  //   assertEquals(true, Animal.all().get(0).equals(firstAnimal));
  //   assertEquals(true, Animal.all().get(1).equals(secondAnimal));
  // }
  //
  // @Test
  // public void find_returnsAnimalWithSameId_secondAnimal() {
  //   Animal firstAnimal = new Animal(1);
  //   firstAnimal.save();
  //   Animal secondAnimal = new Animal(2);
  //   secondAnimal.save();
  //   assertEquals(Animal.find(secondAnimal.getId()), secondAnimal);
  // }
  //
  // @Test
  // public void delete_deletesAnimalFromDatabase_0() {
  //   Animal testAnimal = new Animal(1);
  //   testAnimal.save();
  //   testAnimal.delete();
  //   assertEquals(0, Animal.all().size());
  // }
  //
  // public void updateName_updatesAnimalNameInDatabase_String() {
  //   Animal testAnimal = new Animal(1);
  //   testAnimal.save();
  //   testAnimal.updateName(2);
  //   assertEquals(2, testAnimal.getName());
  // }
  //
  // @Test
  // public void find_returnsNullWhenNoAnimalFound_null() {
  //   assertTrue(Animal.find(999) == null);
  // }

}
