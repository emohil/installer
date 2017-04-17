var app = angular.module('app', ['ui.bootstrap', 'ifu.template', 'ifu.util']);

app.controller('ApManagerController',//
['$scope', '$http', '$uibModal', 'UrlUtil',//
function($scope, $http, $uibModal, UrlUtil) {

  $http.get('apBatch/load.do', {
    params : {
      id : $scope.apBatchId
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  $scope.sf = {
    apBatchId : $scope.apBatchId
  };

  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }
  $scope.query = function() {
    $http.post('apManager/list.do', $scope.sf || {}, {
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
  
  $scope.goBack = function() {
    window.location.href = UrlUtil.transform('apBatch/apBatchList.do');
  }

}]);