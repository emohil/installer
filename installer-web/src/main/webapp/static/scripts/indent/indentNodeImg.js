var app = angular.module('app', ['ui.bootstrap','ifu.util']);

app.controller('IndentNodeImgController',//
['$scope', '$window', 'UrlUtil',//
function($scope, $window, UrlUtil) {

  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }

  $scope.$watch('pager.currentPage', $scope.query);
  
  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('indentNode/indentNodeTree.do?id='+$scope.id);
  }
  
}]);
