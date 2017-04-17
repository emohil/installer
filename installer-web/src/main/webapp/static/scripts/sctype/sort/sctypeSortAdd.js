var app = angular.module('app', ['ui.bootstrap', 'ifu.util']);

app.controller('SctypeSortController', ['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  $scope.data = {
      sctypeId : $scope.sctypeId
  };

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }

    $http.post('sctypeSort/save.do', $scope.data).then(function() {
      $scope.goBack();
    });
  }

  $scope.goBack = function(id) {
    $window.location.href = UrlUtil.transform('sctype/sctypeTree.do');
  }
}]);