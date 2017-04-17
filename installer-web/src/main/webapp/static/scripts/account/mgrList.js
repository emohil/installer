var app = angular.module('app', ['ui.bootstrap']);

app.controller('MgrListController',//
['$scope', '$http', '$window',//
function($scope, $http, $window) {
  
  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }

  $scope.query = function() {
    $http.get('worker/findMgrList.do', {
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
    $window.location.href = "worker/workerList.do";
  }

}]);
