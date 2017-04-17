var app = angular.module('app',// 
['ui.bootstrap', 'ifu.util']);

app.controller('indentEvaluateMsgController', ['$scope', '$http', '$window', 'UrlUtil',
    function($scope, $http, $window, UrlUtil) {

      $http.get("indentEvaluate/findByIndentId.do", {
        params : {
          indentId : $scope.data.id
        }
      }).then(function(response) {
        $scope.data = response.data;
        console.log($scope.data);
      });

      $scope.goBack = function() {
        $window.location.href = UrlUtil.transform('indent/indentList.do');
      }

    }]);
