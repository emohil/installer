var app = angular.module('app',// 
['ui.grid', 'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.bootstrap','ifu.util','ifu.form']);

app.controller('manageExceptionController', ['$scope', '$http', '$window', 'UrlUtil', '$uibModal',//
function($scope, $http, $window, UrlUtil, $uibModal) {

  $scope.contactsDatas = {};

  // load
  $http.get('indentExc/findException.do', {
    params : {
      id : $scope.data.id
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  $scope.save = function() {

    if (!$scope.form1.$valid) {
      var modalScope = $scope.$new(true);
      modalScope.title = "处理异常不完整";
      modalScope.message = "当前异常处理信息不完整，请核对后再保存！";
      $uibModal.open({
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      }); 
      return false;
    }
    $http.post('indentExc/update.do', $scope.data).then(function(response) {
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = "操作成功";
        UrlUtil.autoJump(modalScope, $scope.goBack);
      } else {
        var modalScope = $scope.$new(true);
        modalScope.title = "操作失败";
        modalScope.message = data.err_msg;
        $uibModal.open({
          templateUrl : 'template/modal/alert.html',
          scope : modalScope
        }); 
      }
    });
  }
  
  $scope.goBack = function() {
    window.location.href = UrlUtil.transform('indentExc/indentExcList.do?indentNodeId='+$scope.data.indentNodeId);
  }
  
}]);
