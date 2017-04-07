import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class User {
  private int id;
  private String name;

  public User(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getName().equals(newUser.getName()) &&
             this.getId() == newUser.getId();
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (name) VALUES (:name);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<User> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users;";
      return con.createQuery(sql)
        .executeAndFetch(User.class);
    }
  }

  public static User find(int id) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE id = :id;";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(User.class);
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void update(String name) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Sighting> getSightings() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE userId = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Sighting.class);
    }
  }

}
