$(function() {
  $('[data-toggle="popover"]').popover()
  $('[data-toggle="tooltip"]').tooltip()

  fetch('/speciesJSON').then(function(response) {
    return response.json();
  }).then(function(data) {
    populateForm(data);
  });
  function populateForm(species) {
    species.forEach(function(specie) {
      $("#speciesSelector").append("<option class='speciesSelected' value='" + specie.id + "'>" + specie.name + "</option>");
    })
    $("#speciesSelector").change(function() {
      var selected = $(this).val();
      var match = species.filter(function(obj) {
        return obj.id == selected;
      });
      match = match[0];
      if (match.endangered) {
        $("#endangered").slideDown();
      } else {
        $("#endangered").slideUp();
      }
    })
  }
})
