var app = angular
    .module('app',// 
    ['ui.grid', 'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.bootstrap',
        'ifu.cityselect', 'ifu.form', 'ifu.util']);

app.controller('ApartyAddController',//
['$scope', '$http', '$window', '$uibModal', 'UrlUtil',//
function($scope, $http, $window, $uibModal, UrlUtil) {

  $scope.data = {};
  
  $scope.save = function() {

    if (!$scope.form1.$valid) {
      var modalScope = $scope.$new(true);
      modalScope.title = "信息不完整";
      modalScope.message = "当前表单输入信息不完整，请补充完整后再保存！";
      $uibModal.open({
        backdrop : 'static',
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      });  
      return false;
    }

    return $http.post('aparty/doSave.do', $scope.data).then(function(response) {
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = data.err_msg;
        UrlUtil.autoJump(modalScope, $scope.goBack);
      } else {
        alert(data.err_msg);
      }
    });
  }

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('aparty/apartyList.do');
  }

}]);

app.controller('Contact1Controller', ['$scope', '$http', function($scope, $http) {

  $scope.gridOptions = {
    /***/
    enableColumnMenus : false,
    /***/
    enableSorting : false,
    /***/
    enableCellEditOnFocus : true,
    /***/
    enableCellEdit : true
  };

  $scope.gridOptions.columnDefs = [{
    displayName : '姓名',
    name : 'name1',
    width : 90,
    validators : {
      required : true
    },
    cellTemplate : 'ui-grid/cellTooltipValidator'

  }, {
    displayName : '职务',
    name : 'duty',
    width : 90
  }, {
    displayName : '线条',
    name : 'linellae',
    width : 90
  }, {
    displayName : '手机',
    name : 'mobile',
    width : 90
  }, {
    displayName : '邮箱',
    name : 'email',
    width : 90
  }, {
    displayName : 'QQ',
    name : 'qq',
    width : 80
  }, {
    displayName : '座机',
    name : 'telePhone',
    width : 90
  }, {
    displayName : '传真',
    name : 'fax',
    width : 90
  }, {
    displayName : '操作',
    name : 'operator',
    width : 60,
    enableCellEdit : false,
    cellTemplate : '<button class="glyphicon glyphicon-remove btn btn-xs btn-danger"'//
        + 'ng-click="grid.appScope.deleteRow(row)">移除</button>'//
  }];

  $scope.gridOptions.data = $scope.data.contacts1List = [{}];

  $scope.addRow = function() {
    $scope.gridOptions.data.push({});
  }

  $scope.deleteRow = function(row) {
    var index = $scope.gridOptions.data.indexOf(row.entity);
    $scope.gridOptions.data.splice(index, 1);
  };
}]);

app.controller('Contact2Controller', ['$scope', function($scope) {

  $scope.gridOptions = {
    /***/
    enableColumnMenus : false,
    /***/
    enableSorting : false,
    /***/
    enableCellEditOnFocus : true,
    /***/
    enableCellEdit : true
  };

  $scope.gridOptions.columnDefs = [{
    displayName : '姓名',
    name : 'name1',
    width : 90,
    validators : {
      required : true
    },
    cellTemplate : 'ui-grid/cellTooltipValidator'

  }, {
    displayName : '职务',
    name : 'duty',
    width : 90
  }, {
    displayName : '线条',
    name : 'linellae',
    width : 90
  }, {
    displayName : '手机',
    name : 'mobile',
    width : 90
  }, {
    displayName : '邮箱',
    name : 'email',
    width : 90
  }, {
    displayName : 'QQ',
    name : 'qq',
    width : 80
  }, {
    displayName : '座机',
    name : 'telePhone',
    width : 90
  }, {
    displayName : '传真',
    name : 'fax',
    width : 90
  }, {
    displayName : '操作',
    name : 'operator',
    width : 60,
    enableCellEdit : false,
    cellTemplate : '<button class="glyphicon glyphicon-remove btn btn-xs btn-danger"'//
        + 'ng-click="grid.appScope.deleteRow(row)">移除</button>'//
  }];

  $scope.gridOptions.data = $scope.data.contacts2List = [{}];

  $scope.addRow = function() {
    $scope.gridOptions.data.push({});
  }

  $scope.deleteRow = function(row) {
    var index = $scope.gridOptions.data.indexOf(row.entity);
    $scope.gridOptions.data.splice(index, 1);
  };
}]);