var app = angular.module('app', ['ui.bootstrap']);

app.controller('ProvListController',//
    ['$scope', '$http',//
     function($scope, $http) {
      $scope.pager = {
          currentPage : 1,
          maxSize : 10
      }

      $scope.query = function() {
        $http.post('prov/list.do', $scope.sf || {}, {
          params : {
            page : $scope.pager.currentPage
          }
        }).then(function(response) {
          var data = response.data;
          $scope.rows = data.rows;
          console.log($scope.rows);
        });
      }

      $scope.$watch('pager.currentPage', $scope.query);

    }]);
