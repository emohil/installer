var app = angular.module('app',// 
['ui.grid', 'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.bootstrap', 'ifu.util']);

app.controller('CityAddController', ['$scope', '$http', '$window', 'UrlUtil',
    function($scope, $http, $window, UrlUtil) {

      $scope.save = function() {

        if (!$scope.form1.$valid) {
          return false;
        }
        var he = $scope.data.cityId;
        console.log($scope.data);
        $http.post('area/save.do', $scope.data).then(function() {
          $scope.goBack();
        });
      }
      $scope.goBack = function() {
        $window.location.href = UrlUtil.transform('city/cityList.do?sf.provId=' + $scope.provId);
      }
    }]);

app.controller('AreaController', ['$scope', '$http', function($scope, $http) {

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
    displayName : '区编码',
    name : 'code1',
    width : 120,
    validators : {
      required : true
    },
    cellTemplate : 'ui-grid/cellTooltipValidator'

  }, {
    displayName : '区名称',
    name : 'name1',
    width : 120,
    validators : {
      required : true
    },
    cellTemplate : 'ui-grid/cellTooltipValidator'

  }, {
    displayName : '加价率',
    name : 'addRate',
    width : 120
  }, {
    displayName : '工人上限',
    name : 'maxWorkers',
    width : 120
  }, {
    displayName : '每单额外服务费',
    name : 'extraCost',
    width : 200
  }, {
    displayName : '操作',
    name : 'operator',
    width : 80,
    enableCellEdit : false,
    cellTemplate : '<button class="glyphicon glyphicon-remove btn btn-xs btn-danger"'//
        + 'ng-click="grid.appScope.deleteRow(row)">移除</button>'//
  }];

  $http.get('area/queryAreaList.do', {
    params : {
      cityId : $scope.data.cityId,
    }
  }).then(function(response) {
    $scope.gridOptions.data = $scope.data.areaList = response.data;
  });

  $scope.addRow = function() {
    $scope.gridOptions.data.push({});
  }

  $scope.deleteRow = function(row) {
    var index = $scope.gridOptions.data.indexOf(row.entity);
    $scope.gridOptions.data.splice(index, 1);
  };
}]);
