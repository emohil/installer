var app = angular.module('app', ['ui.bootstrap', 'ifu.util']);

app.controller('AdminAddController',//
['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  $scope.save = function() {
    if (!$scope.form1.$valid) {
      return false;
    }
    
    $http.post('admin/save.do', $scope.data).then($scope.goBack);
  }
  
  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('admin/adminList.do');
  }

}]);