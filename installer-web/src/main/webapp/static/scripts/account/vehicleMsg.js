var app = angular.module('app',// 
['ui.bootstrap', 'ifu.util']);

app.controller('vehicleMsgController', ['$scope', '$http', '$window', 'UrlUtil',
    function($scope, $http, $window, UrlUtil) {

      $http.get("worker/findWorkerById.do", {
        params : {
          id : $scope.data.id
        }
      }).then(function(response) {
        $scope.data = response.data;
      });

      $scope.goBack = function() {
        $window.location.href = UrlUtil.transform('worker/workerList.do');
      }

    }]);
