var app = angular.module('app',// 
['ui.bootstrap', 'ifu.form', 'ifu.util']);

app.controller('ApBatchController',//
['$scope', '$http', '$window', '$filter', '$uibModal', 'UrlUtil',//
function($scope, $http, $window, $filter, $uibModal, UrlUtil) {

  $scope.data = {};

  $scope.search = {};
  $scope.pager = {
    currentPage : 1,
    itemsPerPage : 15,
    maxSize : 10
  }
  
  $scope.rows = [];

  //执行过滤
  $scope.doFilterRows = function() {
    var rows = $filter('filter')($scope.rows, $scope.search);
    var start = ($scope.pager.currentPage - 1) * $scope.pager.itemsPerPage;
    var limit = start + $scope.pager.itemsPerPage;
    $scope.pager.totalItems = rows.length;
    
    $scope.filterRows = rows.slice(start, limit);
  }
  
  $scope.$watch('pager.currentPage', $scope.doFilterRows);
  $scope.$watch('search.code1', $scope.doFilterRows);
  $scope.$watch('search.workerName1', $scope.doFilterRows);
  $scope.$watch('search.managerName1', $scope.doFilterRows);

  $scope.alert = function(title, message) {
    var modalScope = $scope.$new(true);
    modalScope.title = title || "信息不完整";
    modalScope.message = message || "当前表单输入信息不完整，请补充完整后再保存！";
    $uibModal.open({
      templateUrl : 'template/modal/alert.html',
      scope : modalScope
    });
  }

  $scope.doPreview = function() {

    if (!$scope.form1.$valid) {
      $scope.alert();
      return false;
    }

    $http.post('apBatch/doPreview.do', $scope.data).then(function(response) {
      var data = response.data;
      if (data.success) {
        $scope.rows = response.data.rows;
        angular.forEach($scope.rows, function(row) {
          row.checked = true;
        });
      } else {
        $scope.rows = [];
        $scope.alert('无数据', data.err_msg || '没有找到符合条件的记录');
      }
      $scope.doFilterRows();
    });
  }
  
  $scope.doReset = function() {
    $scope.rows = [];
  }

  $scope.doSave = function() {
    if (!$scope.form1.$valid) {
      $scope.alert();
      return false;
    }
    
    $scope.data.indentIdList = [];
    angular.forEach($scope.rows, function(row) {
      if (row.checked) {
        $scope.data.indentIdList.push(row.id);
      }
    });
    if ($scope.data.indentIdList.length == 0) {
      $scope.alert('无数据', '没有勾选需要结算的订单');
      return false;
    }

    $http.post('apBatch/doSave.do', $scope.data).then(function(response) {
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = data.err_msg;
        UrlUtil.autoJump(modalScope, $scope.goBack);
      } else {
        
        $scope.alert('保存失败', data.err_msg || '结算批次保存失败');
      }
    });
  }

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('apBatch/apBatchList.do');
  }

}]);