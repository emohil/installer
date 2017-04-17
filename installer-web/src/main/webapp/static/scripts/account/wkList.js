var app = angular.module('app', ['ui.bootstrap']);

app.controller('WkListController',//
['$scope', '$http', '$window',//
function($scope, $http, $window) {

  $scope.query = function() {
    $http.get('manager/findWkList.do', {
      params : {
        id : $scope.data.id
      }
    }).then(function(response) {
      var data = response.data;
      $scope.rows = data;
    });
  }

  $scope.$watch('pager.currentPage', $scope.query);
  
  $scope.goBack = function () {
    $window.location.href = "manager/managerList.do";
  }

}]);
