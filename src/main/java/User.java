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

}
