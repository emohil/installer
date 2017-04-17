var app = angular.module('app', ['ui.bootstrap']);

app.controller('ItemListController',//
    ['$scope', '$http',//
     function($scope, $http) {
      $scope.pager = {
          currentPage : 1,
          maxSize : 10
      }

      $scope.crtDateBegin = {
          opened : false
      };

      $scope.crtDateEnd = {
          opened : false
      };

      $scope.openCrtDateBegin = function() {
        $scope.crtDateBegin.opened = true;
      }

      $scope.openCrtDateEnd = function() {
        $scope.crtDateEnd.opened = true;
      }

      $scope.beginDate = {
          opened : false
      };

      $scope.overDate = {
          opened : false
      };

      $scope.openBeginDate = function() {
        $scope.beginDate.opened = true;
      }

      $scope.openOverDate = function() {
        $scope.overDate.opened = true;
      }

      $scope.query = function() {
        $http.post('item/list.do', $scope.sf || {}, {
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

    }]);
