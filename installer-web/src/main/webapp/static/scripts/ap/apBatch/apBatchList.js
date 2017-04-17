var app = angular.module('app', ['ui.bootstrap', 'ifu.template', 'ifu.form']);

app.controller('ApBatchController',//
['$scope', '$http', '$uibModal',//
function($scope, $http, $uibModal) {

  $scope.sf = {};

  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }
  $scope.query = function() {
    $http.post('apBatch/list.do', $scope.sf || {}, {
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

  $scope.doDelete = function(id) {
    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要删除该结算批次吗？此操作不可逆! ";
    $uibModal.open({
      templateUrl : 'template/modal/delete.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('apBatch/doDelete.do', {
        params : {
          id : id
        }
      }).then(function(response) {
        var data = response.data;
        if (data.success) {
          var modalScope = $scope.$new(true);
          modalScope.message = data.err_msg;
          
          //reload list
          $scope.query();
        } else {
          alert(data.err_msg);
        }
      });
    });
  }

  $scope.doPost = function(id) {
    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要过账该结算批次吗？过账后将不可更改! ";
    $uibModal.open({
      templateUrl : 'template/modal/confirm.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('apBatch/doPost.do', {
        params : {
          id : id
        }
      }).then(function(response) {
        var data = response.data;
        if (data.success) {
          var modalScope = $scope.$new(true);
          modalScope.message = data.err_msg;
          
          //reload list
          $scope.query();
        } else {
          alert(data.err_msg || '过账失败');
        }
      });
    });
  }
}]);