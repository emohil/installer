var app = angular.module('app',// 
['ui.bootstrap', 'ifu.util']);

app.controller('managerMsgController', ['$scope', '$http', '$window', 'UrlUtil',
    function($scope, $http, $window, UrlUtil) {

      $http.get("manager/findByManagerId.do", {
        params : {
          id : $scope.data.id
        }
      }).then(function(response) {
        $scope.data = response.data;
      });

      $scope.goBack = function() {
        $window.location.href = UrlUtil.transform('manager/managerList.do');
      }

    }]);
