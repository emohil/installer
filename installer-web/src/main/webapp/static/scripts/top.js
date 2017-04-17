var app = angular.module('app');

app.controller('TopController',//
['$scope', '$http', '$interval',//
function($scope, $http, $interval) {

  $scope.exceptionIndentCount = 0;

  $http.get('adminAuth/hasIndentListAuth.do').then(function(response) {
    var ret = response.data;
    if (ret.success && ret.data) {
      $scope.updateExceptionIndentCount();
    }
  });

  $scope.getExceptionIndentCount = function() {
    $http.get('indent/exceptionIndentCount.do').then(function(response) {
      var ret = response.data;
      if (ret.success) {
        $scope.exceptionIndentCount = ret.data || 0;
      }
    });
  }

  $scope.updateExceptionIndentCount = function() {
    $scope.getExceptionIndentCount();
    $interval($scope.getExceptionIndentCount, 1000 * 5 * 60);
  }
}]);
