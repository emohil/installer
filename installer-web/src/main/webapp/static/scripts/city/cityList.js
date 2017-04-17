var app = angular.module('app', ['ui.bootstrap']);

app.controller('CityListController',//
    ['$scope', '$http', '$window',//
     function($scope, $http, $window) {
      $scope.pager = {
          currentPage : 1,
          maxSize : 10
      }

      $scope.query = function() {
        $http.post('city/list.do', $scope.sf || {}, {
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
      
      $scope.goBack = function () {
        $window.location.href = "prov/provList.do";
      }

    }]);
