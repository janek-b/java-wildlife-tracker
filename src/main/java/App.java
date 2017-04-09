import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.text.DateFormat;

import com.google.gson.Gson;

public class App {
  public static void main(String[] args) {
    externalStaticFileLocation(String.format("%s/src/main/resources/public", System.getProperty("user.dir")));

    // staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    Gson gson = new Gson();

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", request.session().attribute("user"));
      model.put("commonSpecies", Species.getMostSighted());
      model.put("template", "templates/index.vtl");
      return render(model, layout);
    });

    post("/login", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String email = request.queryParams("email");
      User user;
      try {
        user = User.findByEmail(email);
      } catch (IllegalArgumentException exception) {
        user = new User(email);
        user.save();
      }
      request.session().attribute("user", user);
      response.redirect(request.headers("Referer"));
      return render(model, layout);
    });

    get("/logout", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      request.session().removeAttribute("user");
      response.redirect(request.headers("Referer"));
      return render(model, layout);
    });

    get("/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", request.session().attribute("user"));
      model.put("users", User.all());
      model.put("speciesGroups", Species.AnimalGroups.values());
      model.put("template", "templates/admin.vtl");
      return render(model, layout);
    });


    post("/users/:id/sightings/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User reportingUser = User.find(Integer.parseInt(request.params(":id")));
      User loggedInUser = request.session().attribute("user");
      if (reportingUser.equals(loggedInUser)) {
        Species species = Species.find(Integer.parseInt(request.queryParams("species")));
        String location = request.queryParams("location");
        Sighting newSighting = new Sighting(species.getId(), location, reportingUser.getId());
        newSighting.save();
        if (request.queryParams("image") != null) {
          newSighting.setImage(request.queryParams("image"));
        } else {
          newSighting.setImage("/images/default-placeholder.png");
        }
        if (species.getEndangered()) {
          String animalHealth = request.queryParams("animalHealth");
          String animalAge = request.queryParams("animalAge");
          String animalIdentifier = request.queryParams("animalIdentifier");
          // add try to find if animal already exists
          Animal newAnimal = new Animal(species.getId(), animalHealth, animalAge, animalIdentifier);
          newAnimal.save();
          newAnimal.addSighting(newSighting);
        }
        response.redirect(request.headers("Referer"));
      } else {
        response.redirect("/");
      }
      return render(model, layout);
    });

    get("/species", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", request.session().attribute("user"));
      model.put("speciesAll", Species.all());
      model.put("template", "templates/species.vtl");
      return render(model, layout);
    });

    get("/species/:speciesId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", request.session().attribute("user"));
      model.put("species", Species.find(Integer.parseInt(request.params(":speciesId"))));
      model.put("speciesGroups", Species.AnimalGroups.values());
      model.put("template", "templates/species-details.vtl");
      return render(model, layout);
    });

    get("/speciesJSON", "application/json", (request, response) -> {
      return gson.toJson(Species.all());
    });

    post("/species/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String speciesName = request.queryParams("speciesName");
      String classification = request.queryParams("classification");
      String habitat = request.queryParams("habitat");
      boolean endangered = (request.queryParamsValues("endangered") != null);
      Species newSpecies = new Species(speciesName, classification, habitat, endangered);
      newSpecies.save();
      if (request.queryParams("image") != null) {
        newSpecies.setImage(request.queryParams("image"));
      } else {
        newSpecies.setImage("/images/default-placeholder.png");
      }
      response.redirect(request.headers("Referer"));
      return render(model, layout);
    });

    post("/species/:speciesId/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Species species = Species.find(Integer.parseInt(request.params(":speciesId")));
      String speciesName = request.queryParams("speciesName");
      String classification = request.queryParams("classification");
      String habitat = request.queryParams("habitat");
      boolean endangered = (request.queryParamsValues("endangered") != null);
      String image = request.queryParams("image");
      species.update(speciesName, habitat, endangered, image);
      response.redirect(request.headers("Referer"));
      return render(model, layout);
    });

    post("/species/:speciesId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Species species = Species.find(Integer.parseInt(request.params(":speciesId")));
      species.delete();
      response.redirect("/species");
      return render(model, layout);
    });

    get("/sightings", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", request.session().attribute("user"));
      model.put("recentSightings", Sighting.getRecentSightings());
      model.put("commonSpecies", Species.getMostSighted());
      model.put("endangeredSightings", Animal.getEndangeredSightings());
      model.put("template", "templates/sightings.vtl");
      return render(model, layout);
    });

    get("/users/:userId", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User loggedInUser = request.session().attribute("user");
      User userProfile = User.find(Integer.parseInt(request.params(":userId")));
      try {
        if (loggedInUser.equals(userProfile)) {
          model.put("user", loggedInUser);
          model.put("userProfile", userProfile);
          model.put("template", "templates/user.vtl");
        } else {
          response.redirect("/");
          // Change to Error page
        }
      } catch (NullPointerException exception) {
        response.redirect("/");
      }
      return render(model, layout);
    });

    post("/users/:userId/update", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User loggedInUser = request.session().attribute("user");
      User user = User.find(Integer.parseInt(request.params(":userId")));
      String userName = request.queryParams("userName");
      String userEmail = request.queryParams("userEmail");
      if (loggedInUser.equals(user)) {
        request.session().removeAttribute("user");
        user.update(userEmail, userName);
        request.session().attribute("user", User.find(user.getId()));
      } else {
        user.update(userEmail, userName);
      }
      response.redirect(request.headers("Referer"));
      return render(model, layout);
    });

    post("/users/:userId/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":userId")));
      user.delete();
      response.redirect(request.headers("Referer"));
      return render(model, layout);
    });

    get("/search", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String searchInput = request.queryParams("searchInput");
      model.put("user", request.session().attribute("user"));
      model.put("speciesResults", Species.search(searchInput));
      model.put("animals", Animal.search(searchInput));
      model.put("template", "templates/result.vtl");
      return render(model, layout);
    });

  }

  public static String render(Map<String, Object> model, String templatePath) {
    model.put("healthOptions", Animal.Health.values());
    model.put("ageOptions", Animal.Age.values());
    model.put("formatter", DateFormat.getDateTimeInstance());
    return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
  }

}
