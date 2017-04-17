var app = angular.module('app',// 
    ['ui.grid', 'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.bootstrap',
     'ifu.cityselect', 'ifu.form', 'ifu.template', 'ifu.util']);

app.controller('ApartyController', ['$scope', '$http', '$window', '$uibModal', 'UrlUtil',//
                                    function($scope, $http, $window, $uibModal, UrlUtil) {

  $scope.contactsDatas = {};

  // load
  $http.get('aparty/load.do', {
    params : {
      id : $scope.data.id
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  $scope.save = function() {

    if (!$scope.form1.$valid) {
      return false;
    }
    for ( var d in $scope.contactsDatas) {
      $scope.data[d] = $scope.contactsDatas[d];
    }
    $http.post('aparty/update.do', $scope.data).then(function() {
      $window.location.href = UrlUtil.transform('aparty/apartyList.do');
    });
  }
  
  $scope.doDelete = function() {
    var id = $scope.data.id;
    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要删除该甲方信息吗？如果该甲方已发布项目，将不能删除! ";
    $uibModal.open({
      templateUrl : 'template/modal/delete.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('aparty/deleteAparty.do', {
        params : {
          id : id
        }
      }).then(function(response) {
        var data = response.data;
        if (data.success) {
          var modalScope = $scope.$new(true);
          modalScope.message = data.err_msg;
          UrlUtil.autoJump(modalScope, $scope.goBack);
        } else {
          alert(data.err_msg);
        }

      });
    });
  
  }

  $scope.goBack = function() {
    window.location.href = UrlUtil.transform('aparty/apartyList.do');
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

  $http.get('apartyContacts/queryContactsList.do', {
    params : {
      apartyId : $scope.data.id,
      stamp : 'APARTY'
    }
  }).then(function(response) {
    $scope.gridOptions.data = $scope.contactsDatas.contacts1List = response.data;
  });

  $scope.addRow = function() {
    $scope.gridOptions.data.push({});
  }

  $scope.deleteRow = function(row) {
    var index = $scope.gridOptions.data.indexOf(row.entity);
    $scope.gridOptions.data.splice(index, 1);
  };
}]);

app.controller('Contact2Controller', ['$scope', '$http', function($scope, $http) {

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

  $http.get('apartyContacts/queryContactsList.do', {
    params : {
      apartyId : $scope.data.id,
      stamp : 'BPARTY'
    }
  }).then(function(response) {
    $scope.gridOptions.data = $scope.contactsDatas.contacts2List = response.data;
  });

  $scope.addRow = function() {
    $scope.gridOptions.data.push({});
  }

  $scope.deleteRow = function(row) {
    var index = $scope.gridOptions.data.indexOf(row.entity);
    $scope.gridOptions.data.splice(index, 1);
  };
}]);