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
      model.put("sightings", Sighting.all());
      model.put("formatter", DateFormat.getDateTimeInstance());
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
        response.redirect(request.headers("Referer"));
      } else {
        response.redirect("/");
      }
      return render(model, layout);
    });

    get("/species", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("user", request.session().attribute("user"));
      model.put("species", Species.all());
      model.put("template", "templates/species.vtl");
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
      response.redirect(request.headers("Referer"));
      return render(model, layout);
    });

    // post("/endangered_sighting", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   String rangerName = request.queryParams("rangerName");
    //   int animalIdSelected = Integer.parseInt(request.queryParams("endangeredAnimalSelected"));
    //   String latLong = request.queryParams("latLong");
    //   Sighting sighting = new Sighting(animalIdSelected, latLong, rangerName);
    //   sighting.save();
    //   model.put("sighting", sighting);
    //   model.put("animals", EndangeredAnimal.all());
    //   String animal = EndangeredAnimal.find(animalIdSelected).getName();
    //   model.put("animal", animal);
    //   model.put("template", "templates/success.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/sighting", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   String rangerName = request.queryParams("rangerName");
    //   int animalIdSelected = Integer.parseInt(request.queryParams("animalSelected"));
    //   String latLong = request.queryParams("latLong");
    //   Sighting sighting = new Sighting(animalIdSelected, latLong, rangerName);
    //   sighting.save();
    //   model.put("sighting", sighting);
    //   model.put("animals", Animal.all());
    //   String animal = Animal.find(animalIdSelected).getName();
    //   model.put("animal", animal);
    //   model.put("template", "templates/success.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/animal/new", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   model.put("animals", Animal.all());
    //   model.put("endangeredAnimals", EndangeredAnimal.all());
    //   model.put("template", "templates/animal-form.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/animal/new", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   boolean endangered = request.queryParamsValues("endangered")!=null;
    //   if (endangered) {
    //     String name = request.queryParams("name");
    //     String health = request.queryParams("health");
    //     String age = request.queryParams("age");
    //     EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name, health, age);
    //     endangeredAnimal.save();
    //     model.put("animals", Animal.all());
    //     model.put("endangeredAnimals", EndangeredAnimal.all());
    //   } else {
    //     String name = request.queryParams("name");
    //     Animal animal = new Animal(name);
    //     animal.save();
    //     model.put("animals", Animal.all());
    //     model.put("endangeredAnimals", EndangeredAnimal.all());
    //   }
    //   response.redirect("/");
    //     return null;
    //   });
    //
    // get("/animal/:id", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   Animal animal = Animal.find(Integer.parseInt(request.params("id")));
    //   model.put("animal", animal);
    //   model.put("template", "templates/animal.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/endangered_animal/:id", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   EndangeredAnimal endangeredAnimal = EndangeredAnimal.find(Integer.parseInt(request.params("id")));
    //   model.put("endangeredAnimal", endangeredAnimal);
    //   model.put("template", "templates/endangered_animal.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/error", (request, response) -> {
    //   Map<String, Object> model = new HashMap<String, Object>();
    //   model.put("template", "templates/error.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());

  }

  public static String render(Map<String, Object> model, String templatePath) {
    model.put("healthOptions", Animal.Health.values());
    model.put("ageOptions", Animal.Age.values());
    return new VelocityTemplateEngine().render(new ModelAndView(model, templatePath));
  }

}
