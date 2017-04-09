import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;

public class User implements DatabaseManagement {
  private int id;
  private String email;
  private String name;

  public User(String email) {
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherUser) {
    if (!(otherUser instanceof User)) {
      return false;
    } else {
      User newUser = (User) otherUser;
      return this.getEmail().equals(newUser.getEmail()) &&
             this.getId() == newUser.getId();
    }
  }

  @Override
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO users (email) VALUES (:email);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("email", this.email)
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

  @Override
  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM users WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void update(String email, String name) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "UPDATE users SET (email, name) = (:email, :name) WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("email", email)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static User findByEmail(String email) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM users WHERE email = :email;";
      User user = con.createQuery(sql)
        .addParameter("email", email)
        .executeAndFetchFirst(User.class);
      if (user == null) {
        throw new IllegalArgumentException("No user with that email.");
      }
      return user;
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
