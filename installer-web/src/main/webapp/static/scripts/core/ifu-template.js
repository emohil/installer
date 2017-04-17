angular.module("ifu.template", []).run(["$templateCache", function($templateCache) {
  $templateCache.put("template/modal/delete.html", //
  "<div class=\"modal-header\">" + //
  "  <h3 class=\"modal-title\">" + //
  "    <span class=\"glyphicon glyphicon-exclamation-sign text-danger\"></span>&nbsp;删除" + //
  "  </h3>" + //
  "</div>" + //
  "<div class=\"modal-body\">" + //
  "  {{message || \"您确认要删除吗？\"}}" + //
  "</div>" + //
  "<div class=\"modal-footer\">" + //
  "  <button class=\"btn btn-primary\" type=\"button\" ng-click=\"$close()\">" + //
  "    <span class=\"glyphicon glyphicon-ok\"></span>&nbsp;确认" + //
  "  </button>" + // //
  "  <button class=\"btn btn-warning\" type=\"button\" ng-click=\"$dismiss()\">" + //
  "    <span class=\"glyphicon glyphicon-remove\"></span>&nbsp;取消" + //
  "  </button>" + //
  "</div>");

  $templateCache.put("template/modal/confirm.html", //
  "<div class=\"modal-header\">" + //
  "  <h3 class=\"modal-title\">" + //
  "    <span class=\"glyphicon glyphicon-exclamation-sign text-warning\"></span>&nbsp;确认" + //
  "  </h3>" + //
  "</div>" + //
  "<div class=\"modal-body\">" + //
  "  {{message || \"您确认要继续吗？\"}}" + //
  "</div>" + //
  "<div class=\"modal-footer\">" + //
  "  <button class=\"btn btn-primary\" type=\"button\" ng-click=\"$close()\">" + //
  "    <span class=\"glyphicon glyphicon-ok\"></span>&nbsp;确认" + //
  "  </button>" + // //
  "  <button class=\"btn btn-warning\" type=\"button\" ng-click=\"$dismiss()\">" + //
  "    <span class=\"glyphicon glyphicon-remove\"></span>&nbsp;取消" + //
  "  </button>" + //
  "</div>");

  $templateCache.put("template/modal/alert.html", //
  "<div class=\"modal-header\">" + //
  "  <h3 class=\"modal-title\">" + //
  "    <span class=\"glyphicon glyphicon-exclamation-sign text-warning\"></span>&nbsp;{{title}}" + //
  "  </h3>" + //
  "</div>" + //
  "<div class=\"modal-body\">" + //
  "  {{message}}" + //
  "</div>" + //
  "<div class=\"modal-footer\">" + //
  "  <button class=\"btn btn-warning\" type=\"button\" ng-click=\"$dismiss()\">" + //
  "    <span class=\"glyphicon glyphicon-remove\"></span>&nbsp;知道啦！" + //
  "  </button>" + // //
  "</div>");

  $templateCache.put("template/modal/autoJump.html", //
  "<div class=\"modal-header\">" + //
  "  <h3 class=\"modal-title\">" + //
  "    <span class=\"glyphicon glyphicon-exclamation-sign text-success\"></span>&nbsp;跳转中" + //
  "  </h3>" + //
  "</div>" + //
  "<div class=\"modal-body\">" + //
  "  {{message || \"操作已成功\"}},&nbsp;<span class=\"badge\">{{countdown}}</span>&nbsp;秒钟后自动跳转!" + //
  "</div>" + //
  "<div class=\"modal-footer\">" + //
  "  <button class=\"btn btn-primary\" type=\"button\" ng-click=\"$close()\">" + //
  "    <span class=\"glyphicon glyphicon-forward\"></span>&nbsp;立即跳转" + //
  "  </button>" + // //
  "</div>");
}]);