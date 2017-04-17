var app = angular.module('app',// 
['ui.bootstrap','ifu.template', 'ifu.cityselect2', 'ifu.util']);

app.controller('IndentController',//
[ '$scope', '$http', '$window','$uibModal', 'UrlUtil',
  function($scope, $http, $window, $uibModal, UrlUtil) {
  
  $scope.planDate = {
      opened : false
  };

  $scope.openPlanDate = function() {
    $scope.planDate.opened = true;
  }

  
  $http.get("indent/loadExtraIndent.do", {
    params : {
      indentId : $scope.data.id
    }
  }).then(function(response) {
    $scope.data = response.data.data;
  });

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('indent/indentList.do');
  }
  
  $scope.doSave = function() {
  
    if (!$scope.form1.$valid) {
      var modalScope = $scope.$new(true);
      modalScope.title = "信息不完整";
      modalScope.message = "当前表单输入信息不完整，请补充完整后再保存！";
      $uibModal.open({
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      }); 
      return false;
    }
    
    return $http({
      method : 'POST',
      url : 'indent/doSaveExtraIndent.do',
      data : {
        data : $scope.data,
      }
    }).then(function(response) {
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = data.err_msg;
        UrlUtil.autoJump(modalScope, $scope.goBack);
      } else {
        var modalScope = $scope.$new(true);
        modalScope.title = "保存二次上门订单失败！";
        modalScope.message = data.err_msg;
        $uibModal.open({
          templateUrl : 'template/modal/alert.html',
          scope : modalScope
        }); 
      }
    });
  
  };
  $scope.doDelete = function() {
    var id = $scope.data.id;
    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要删除该订单吗？";
    $uibModal.open({
      templateUrl : 'template/modal/delete.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('indent/indentDelete.do', {
        params : {
          id : id
        }
      }).then(function (response) {
        var data = response.data;
        if (data.success) {
          var modalScope = $scope.$new(true);
          modalScope.message = data.err_msg;
          UrlUtil.autoJump(modalScope, $scope.goBack);
        } else {
          var modalScope = $scope.$new(true);
          modalScope.title = "删除订单失败";
          modalScope.message = data.err_msg;
          $uibModal.open({
            templateUrl : 'template/modal/alert.html',
            scope : modalScope
          }); 
        }
      });
    });
  };
}]);
