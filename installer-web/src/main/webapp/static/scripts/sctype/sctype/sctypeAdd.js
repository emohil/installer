var app = angular.module('app', ['ui.bootstrap', 'ifu.util']);

app.controller('SctypeController', ['$scope', '$http', '$window', 'UrlUtil', //
function($scope, $http, $window, UrlUtil) {

  $scope.data = {};

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }

    $http.post('sctype/save.do', $scope.data).then(function() {
      $scope.goBack();
    });
  }

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('sctype/sctypeTree.do');
  }
}]);
