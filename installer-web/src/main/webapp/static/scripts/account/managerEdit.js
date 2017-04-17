var app = angular.module('app',// 
['ui.bootstrap', 'ifu.util']);

app.controller('managerEditController', ['$scope', '$http', '$window', 'UrlUtil',
    function($scope, $http, $window, UrlUtil) {

      $http.get("manager/findByManagerId.do", {
        params : {
          id : $scope.data.id
        }
      }).then(function(response) {
        $scope.data = response.data;
        console.log($scope.data);
      });

      $scope.save = function() {

        if (!$scope.form1.$valid) {
          return false;
        }
        $http.post('manager/checkManager.do', $scope.data).then(function(response) {
          var data = response.data;
          if (data.success) {
            var modalScope = $scope.$new(true);
            modalScope.message = "操作成功！";
            UrlUtil.autoJump(modalScope, $scope.goBack);
          } else {
            alert("操作失败，请稍后重试！");
          }
        });
      }
      $scope.goBack = function() {
        $window.location.href = UrlUtil.transform('manager/managerList.do');
      }

    }]);
