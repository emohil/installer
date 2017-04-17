
$(document).ready(function() {
  
  var skill = $("#skillScore").val();
  var serve = $("#serveScore").val();
  if (skill == 1) {
    $("#skillScore1 > img").attr({src : "static/images/donestar1.png"});
  } else if (skill == 2) {
    $("#skillScore1 > img").attr({src : "static/images/donestar2.png"});
  } else if (skill == 3) {
    $("#skillScore1 > img").attr({src : "static/images/donestar3.png"});
  } else if (skill == 4) {
    $("#skillScore1 > img").attr({src : "static/images/donestar4.png"});
  } else if (skill == 5) {
    $("#skillScore1 > img").attr({src : "static/images/donestar5.png"});
  }
  if (serve == 1) {
    $("#serveScore1 > img").attr({src : "static/images/donestar1.png"});
  } else if (serve == 2) {
    $("#serveScore1 > img").attr({src : "static/images/donestar2.png"});
  } else if (serve == 3) {
    $("#serveScore1 > img").attr({src : "static/images/donestar3.png"});
  } else if (serve == 4) {
    $("#serveScore1 > img").attr({src : "static/images/donestar4.png"});
  } else if (serve == 5) {
    $("#serveScore1 > img").attr({src : "static/images/donestar5.png"});
  }

});
