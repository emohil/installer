var app = angular.module('app', ['ui.bootstrap']);

app.controller('AdminListController',//
['$scope', '$http',//
function($scope, $http) {

  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }

  $scope.query = function() {
    $http.post('admin/list.do', $scope.sf || {}, {
      params : {
        page : $scope.pager.currentPage
      }
    }).then(function(response) {
      var data = response.data;
      $scope.pager.totalItems = data.page.total;
      $scope.pager.itemsPerPage = data.page.pageSize;

      $scope.rows = data.rows;
    });
  }

  $scope.$watch('pager.currentPage', $scope.query);
  
  $scope.enable = function(id) {
    $http.get('admin/enable.do', {
      params : {
        id : id
      }
    }).then($scope.query);
  }
  
  $scope.disable = function(id) {
    $http.get('admin/disable.do', {
      params : {
        id : id
      }
    }).then($scope.query);
  }

}]);
