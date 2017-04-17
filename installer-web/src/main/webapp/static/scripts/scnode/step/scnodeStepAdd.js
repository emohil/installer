var app = angular.module('app', ['ui.bootstrap', 'ifu.form', 'ifu.util']);

app.controller('ScnodeStepController', ['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  $scope.data = {
      scnodeId : $scope.scnodeId
  };

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }

    $http.post('scnodeStep/save.do', $scope.data).then(function() {
      $scope.goBack();
    });
  }

  $scope.goBack = function(id) {
    $window.location.href = UrlUtil.transform('scnode/scnodeTree.do');
  }
}]);