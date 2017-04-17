var app = angular.module('app', ['ui.bootstrap', 'ifu.util']);

app.controller('SctypeSortController', ['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  // load
  $http.get('sctypeSort/load.do', {
    params : {
      id : $scope.id
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }
    $http.post('sctypeSort/update.do', $scope.data).then(function() {
      $scope.goBack();
    });
  }

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('sctype/sctypeTree.do');
  }
}]);