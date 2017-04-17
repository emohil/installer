var app = angular.module('app', ['ui.bootstrap', 'ifu.cityselect', 'ifu.template']);

app.controller('ApartyListController',//
['$scope', '$http', '$uibModal',//
function($scope, $http, $uibModal) {
  
  $scope.sf = {};

  $scope.crtDateBegin = {
    opened : false
  };
  
  $scope.crtDateEnd = {
      opened : false
    };

  $scope.openCrtDateBegin = function() {
    $scope.crtDateBegin.opened = true;
  }
  
  $scope.openCrtDateEnd = function() {
    $scope.crtDateEnd.opened = true;
  }

  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }

  $scope.query = function() {
    $http.post('aparty/list.do', $scope.sf || {}, {
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
  
  $scope.doClose = function(id) {
    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要停用该甲方吗？";
    $uibModal.open({
      templateUrl : 'template/modal/confirm.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('aparty/controlAparty.do', {
        params : {
          id : id,
          mark : '1'
        }
      }).then(function(response) {
        var data = response.data;
        if (data.success) {
          location.replace(location);
        }
      });
    });
  }

  $scope.doOpen = function(id) {
    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要启用该甲方吗？";
    $uibModal.open({
      templateUrl : 'template/modal/confirm.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('aparty/controlAparty.do', {
        params : {
          id : id,
          mark : '2'
        }
      }).then(function(response) {
        var data = response.data;
        if (data.success) {
          location.replace(location);
        }
      });
    });
  }
  
  $scope.$watch('pager.currentPage', $scope.query);

}]);
