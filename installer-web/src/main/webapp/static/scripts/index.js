$(document).ready(function() {

  var $loginForm = document.forms[0];
  var $user = $("#user");
  var $pwd = $("#pwd");
  var $userTip = $("#userTip");
  var $pwdTip = $("#pwdTip");

  $('.placeholder').click(function() {
    $(this).addClass('hide');
    $(this).prev().children('input').focus();
  });

  var $tipHander = function(tip) {
    if (!$(this).val()) {
      tip.removeClass('hide');
    } else {
      tip.addClass('hide');
    }
  }
  $user.blur(function() {
    $tipHander.call($(this), $userTip);
  });
  $user.keyup(function(e) {
    $tipHander.call($(this), $userTip);
  });
  $pwd.blur(function() {
    $tipHander.call($(this), $pwdTip);
  });
  $pwd.keyup(function(e) {
    if (e.keyCode == 9)// tab
      return;
    $tipHander.call($(this), $pwdTip);
  });

  $loginForm.onsubmit = function() {
    $tipHander.call($user, $userTip);
    $tipHander.call($pwd, $pwdTip);
    if (!$user.val() || !$pwd.val()) {
      return false;
    }
  }
});