var app = angular.module('app', ['ui.bootstrap', 'ifu.cityselect2', 'ifu.template', 'ifu.util']);

app.controller('IndentListController',//
['$scope', '$http', '$uibModal', 'UrlUtil', '$window',//
function($scope, $http, $uibModal, UrlUtil, $window) {

  $scope.sf = {};
  // 将状态赋值给sf
  // 不采用ng-init直接赋值给sf.status，在于sf作用域的区别
  if ($scope.status) {
    $scope.sf.status = $scope.status;
  }

  $scope.sf.executeStatusList = [];

  $scope.pager = {
    currentPage : 1,
    maxSize : 10
  }

  $scope.comitDateBegin = {
    opened : false
  };

  $scope.comitDateEnd = {
    opened : false
  };

  $scope.openComitDateBegin = function() {
    $scope.comitDateBegin.opened = true;
  }

  $scope.openComitDateEnd = function() {
    $scope.comitDateEnd.opened = true;
  }

  $scope.gotDateBegin = {
    opened : false
  };

  $scope.gotDateEnd = {
    opened : false
  };

  $scope.openGotDateBegin = function() {
    $scope.gotDateBegin.opened = true;
  }

  $scope.openGotDateEnd = function() {
    $scope.gotDateEnd.opened = true;
  }

  $scope.query = function() {
    if ($scope.itemId != "") {
      $scope.sf.itemId = $scope.itemId;
      $http.get('item/load.do', {
        params : {
          id : $scope.itemId
        }
      })//
      .then(function(response) {
        $scope.sf.item = response.data;
      });
    }
    if ($scope.mark == 1) {
      $scope.sf.executeStatusList.push('AFTER');
      $scope.sf.executeStatusList.push('CENTRE');
    }
    $http.post('indent/list.do', $scope.sf || {}, {
      params : {
        page : $scope.pager.currentPage
      }
    }).then(function(response) {
      var data = response.data;
      $scope.pager.totalItems = data.page.total;
      $scope.pager.itemsPerPage = data.page.pageSize;

      $scope.rows = data.rows;
      console.log($scope.rows);
    });
  }

  $scope.goList = function() {
    $window.location.href = UrlUtil.transform('indent/indentList.do');
  }
  
  $scope.extraIndent = function(id) {
    $http.get('indent/doExtraIndentAdd.do', {
      params : {
        indentId : id
      }
    })//
    .then(function(response) {
      var data = response.data;
      if (!data.success) {
        var modalScope = $scope.$new(true);
        modalScope.title = "添加二次上门单失败!";
        modalScope.message = data.err_msg;
        $uibModal.open({
          templateUrl : 'template/modal/alert.html',
          scope : modalScope
        }); 
      } else {
        $window.location.href = UrlUtil.transform('indent/extraIndentAdd.do?indentId='+ id);
      }
    });
  }
  $scope.pushIndent = function(id) {
    $http.get('indent/indentPush.do', {
      params : {
        indentId : id
      }
    })//
    .then(function(response) {
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.title = "发布订单成功!";
        modalScope.message = data.err_msg;
        UrlUtil.autoJump(modalScope, $scope.goList);
      } else {
        var modalScope = $scope.$new(true);
        modalScope.title = "发布订单失败!";
        modalScope.message = data.err_msg;
        $uibModal.open({
          templateUrl : 'template/modal/alert.html',
          scope : modalScope
        }); 
      }
    });
  }
  $scope.$watch('pager.currentPage', $scope.query);
}]);