var app = angular.module('app', ['ui.bootstrap', 'ifu.util']);

app.controller('RoleEditController',//
['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  $http.get('role/load.do', {
    params : {
      id : $scope.id
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  $scope.doSave = function() {
    if (!$scope.form1.$valid) {
      return false;
    }

    if ($scope.data.isSystem == 1) {
      return;
    }

    $http.post('role/update.do', $scope.data).then($scope.goBack);
  }

  $scope.doDelete = function() {

    if ($scope.data.isSystem == 1) {
      return;
    }
    
    var id = $scope.data.id;

    if (!confirm('您确定要删除当前角色吗？'))
      return false;

    $http.get('role/doDelete.do', {
      params : {
        id : id
      }
    }).then($scope.goBack);
  };

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('role/roleList.do');
  }
}]);