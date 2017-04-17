var app = angular.module('app', ['ui.bootstrap', 'ifu.util']);

app.controller('SctypeNodeController', ['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  // load
  $http.get('sctypeNode/load.do', {
    params : {
      sctypeId : $scope.sctypeId
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }
    $http.post('sctypeNode/update.do', $scope.data).then(function() {
      $scope.goBack();
    });
  }

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('sctype/sctypeTree.do');
  }
}]);