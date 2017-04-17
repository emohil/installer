var app = angular.module('app', ['ui.bootstrap', 'ifu.template', 'ifu.form']);

app.controller('ScheduleController',//
['$scope', '$http', '$uibModal',//
function($scope, $http, $uibModal) {

  $scope.sf = {};

  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }
  $scope.query = function() {
    $http.post('schedule/list.do', $scope.sf || {}, {
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

  $scope.doEnable = function(id) {
    $http.get('schedule/doEnable.do', {
      params : {
        id : id
      }
    }).then(function(response) {
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = data.err_msg;

        // reload list
        $scope.query();
      } else {
        alert(data.err_msg || '启用失败');
      }
    });
  }
  
  $scope.doDisable = function(id) {
    $http.get('schedule/doDisable.do', {
      params : {
        id : id
      }
    }).then(function(response) {
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = data.err_msg;

        // reload list
        $scope.query();
      } else {
        alert(data.err_msg || '停用失败');
      }
    });
  }
  $scope.doTrigger = function(id) {
    $http.get('schedule/doTrigger.do', {
      params : {
        id : id
      }
    }).then(function(response) {
      var data = response.data;
      if (data.success) {
        alert("运行成功");
      } else {
        alert(data.err_msg || '停用失败');
      }
    });
  }
}]);