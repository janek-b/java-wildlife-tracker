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

public class UserTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void user_instantiatesCorrectly_false() {
    User testUser = new User("Ranger Avery");
    assertEquals(true, testUser instanceof User);
  }

  @Test
  public void getName_UserInstantiatesWithName_Deer() {
    User testUser = new User("Ranger Avery");
    assertEquals("Ranger Avery", testUser.getName());
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    User firstUser = new User("Ranger Avery");
    User anotherUser = new User("Ranger Avery");
    assertTrue(firstUser.equals(anotherUser));
  }

  @Test
  public void save_assignsIdToObjectAndSavesObjectToDatabase() {
    User testUser = new User("Ranger Avery");
    testUser.save();
    User savedUser = User.all().get(0);
    assertEquals(testUser.getId(), savedUser.getId());
  }

  @Test
  public void all_returnsAllInstancesOfUser_false() {
    User firstUser = new User("Ranger Avery");
    firstUser.save();
    User secondUser = new User("Ranger Jeff");
    secondUser.save();
    assertEquals(true, User.all().get(0).equals(firstUser));
    assertEquals(true, User.all().get(1).equals(secondUser));
  }

  @Test
  public void find_returnsUserWithSameId_secondUser() {
    User firstUser = new User("Ranger Avery");
    firstUser.save();
    User secondUser = new User("Ranger Jeff");
    secondUser.save();
    assertEquals(User.find(secondUser.getId()), secondUser);
  }

  @Test
  public void delete_deletesUserFromDatabase_0() {
    User testUser = new User("Ranger Avery");
    testUser.save();
    testUser.delete();
    assertEquals(0, User.all().size());
  }

  public void update_updatesUserPropertiesInDatabase_String() {
    User testUser = new User("Ranger Avery");
    testUser.save();
    testUser.update("Ranger Jeff");
    assertEquals("Ranger Jeff", testUser.getName());
  }

  public void getSightings_returnsAllSightingsByAUser() {
    User testUser = new User("Ranger Avery");
    testUser.save();
    Species testSpecies = new Species("Deer", "Mammal", "Forest", false);
    testSpecies.save();
    Sighting testSighting = new Sighting(testSpecies.getId(), "45.472428, -121.946466", testUser.getId());
    testSighting.save();
    Species secondSpecies = new Species("Black Bear", "Mammal", "Forest", false);
    secondSpecies.save();
    Sighting secondTestSighting = new Sighting (secondSpecies.getId(), "45.472428, -121.946466", testUser.getId());
    secondTestSighting.save();
    Sighting thirdTestSighting = new Sighting (secondSpecies.getId(), "45.472428, -121.946466", 1);
    thirdTestSighting.save();
    Sighting[] sightings = new Sighting[] {testSighting, secondTestSighting};
    assertTrue(testUser.getSightings().containsAll(Arrays.asList(sightings)));
    assertFalse(testUser.getSightings().contains(thirdTestSighting));
  }

}
