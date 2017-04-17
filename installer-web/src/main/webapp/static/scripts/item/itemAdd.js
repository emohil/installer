var app = angular
.module('app',// 
    ['ui.grid', 'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.bootstrap',
     'ifu.cityselect2', 'ifu.form', 'ifu.util']);

app.controller('ItemAddController', ['$scope', '$http', '$window', 'UrlUtil', '$uibModal',
                                     function($scope, $http, $window, UrlUtil, $uibModal) {
  
  $scope.beginDate = {
      opened : false
  };

  $scope.overDate = {
      opened : false
  };

  $scope.openBeginDate = function() {
    $scope.beginDate.opened = true;
  }

  $scope.openOverDate = function() {
    $scope.overDate.opened = true;
  }

  $scope.data = {};
  
  $scope.rt = {};

  $scope.data.itemPriceList=[];

  var count = 0;
  $scope.doTypeaheadApartys = function(q) {
    $scope.apartyId = '';
    return $http.post('aparty/doTypeahead.do', {
      q : q
    }).then(function(response) {
      return response.data;
    });
  };

  $scope.onTypeaheadApartySelect = function($item, $model, $label) {
    $scope.data.apartyId = $item.value;
  };

  $scope.add = function() {
    
    if (!$scope.form1.regionProv.$valid) {
      var modalScope = $scope.$new(true);
      modalScope.title = "请先选择服务地址";
      modalScope.message = "请先选择服务地址！";
      $uibModal.open({
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      }); 
      return false;
    }

    $scope.rt.itemPriceList1 = [];

    for (var int = 0; int < count; int++) {
      $scope.rt.itemPriceList1.push({'serveContentId':$("#serveContentId"+[int]).val(),
        'actualPrice':$("#actualPrice"+[int]).val(),
        'basePrice':$("#baseQuote"+[int]).val(),
        'numerationPrice':$("#numerationPrice"+[int]).val(),
        'workerRate':$("#workerRate"+[int]).val(),
        'managerRate':$("#managerRate"+[int]).val(),
        'profitRate':$("#profitRate"+[int]).val(),
        'name1':$("#name1"+[int]).val(),
        'unit':$("#unit"+[int]).val()
      });
    };

    var checkbox1 = $("[type=checkbox]");
    var sortIds = [];
    checkbox1.each(function(i) {
      if ($(this).prop('checked')) {
        sortIds.push($(this).val());
      }
    });
    $http({
      method : 'POST',
      url : 'sctypeSort/typeList.do',
      headers : {
        'Content-Type' : undefined
      },
      transformRequest : function(data) {
        var formData = new FormData();
        
        formData.append("data", angular.toJson(data.data));
        
        formData.append("sortIds", data.sortIds);
        
        return formData;
      },
      data : {
        sortIds : sortIds,
        data : $scope.data
      }
    }).then(function(response) {
      var index = 0;
      $("#offer tbody").empty();
      for (var i = 0; i < response.data.length; i++) {
        var type = response.data[i];
        for (var j = 0; j < type.sortList.length; j++) {
          var sort = type.sortList[j];
          for (var k = 0; k < sort.contentList.length; k++) {
            var content = sort.contentList[k];
            var flag = false;
            angular.forEach($scope.rt.itemPriceList1, function(data) {
              if (data.serveContentId == content.id) {
                $("#offer tbody").append('<tr><td id="typeName'+index+'">'+type.name1
                    +'</td><td id="sortName'+index+'">'+sort.name1
                    +'<input type="hidden" id="serveContentId'+index+'" value="'+content.id+'"/>'
                    +'<input type="hidden" id="baseQuote'+index+'" value="'+content.baseQuote+'"/>'
                    +'<input type="hidden" id="numerationPrice'+index+'" value="'+content.numerationPrice+'"/>'
                    +'</td><td><input type="hidden" id="name1'+index+'" value="'+content.desc1+'">'+content.desc1
                    +'</td><td><input type="hidden" id="unit'+index+'" value="'+content.unit+'">'+content.unitDisp
                    +'</td><td>￥<input type="text" id="actualPrice'+index+'" value="'+data.actualPrice
                    +'"></td><td><input type="text" id="workerRate'+index+'" onblur="jian('+index+');" value="'+data.workerRate+'"/>%'
                    +'</td><td><input type="text" id="managerRate'+index+'" onblur="jian('+index+');" value="'+data.managerRate+'"/>%'
                    +'</td><td><input type="text" id="profitRate'+index+'" disabled value="'+data.profitRate+'"/>%</td></tr>');
                index++;
                flag = true;
              }
            })
            if (flag) {
              continue;
            }
            $("#offer tbody").append('<tr><td id="typeName'+index+'">'+type.name1
                +'</td><td id="sortName'+index+'">'+sort.name1
                +'<input type="hidden" id="serveContentId'+index+'" value="'+content.id+'"/>'
                +'<input type="hidden" id="baseQuote'+index+'" value="'+content.baseQuote+'"/>'
                +'<input type="hidden" id="numerationPrice'+index+'" value="'+content.numerationPrice+'"/>'
                +'</td><td><input type="hidden" id="name1'+index+'" value="'+content.desc1+'">'+content.desc1
                +'</td><td><input type="hidden" id="unit'+index+'" value="'+content.unit+'">'+content.unitDisp
                +'</td><td>￥<input type="text" id="actualPrice'+index+'" value="'+content.actualPrice
                +'"></td><td><input type="text" id="workerRate'+index+'" onblur="jian('+index+');" value="70"/>%'
                +'</td><td><input type="text" id="managerRate'+index+'" onblur="jian('+index+');" value="5"/>%'
                +'</td><td><input type="text" id="profitRate'+index+'" disabled value="25"/>%</td></tr>');
            index++;
          }
        }
      }
      $("#offer tbody input").css({'width':'100px','border':'1px solid #ccc'});
      count = index;
    });
  }

  $scope.save = function() {

    if (!$scope.form1.regionProv.$valid) {
      var modalScope = $scope.$new(true);
      modalScope.title = "当前信息填写不完整，请完善";
      modalScope.message = "当前信息填写不完整，请完善！";
      $uibModal.open({
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      }); 
      return false;
    }

    for (var int = 0; int < count; int++) {
      $scope.data.itemPriceList.push({'serveContentId':$("#serveContentId"+[int]).val(),
        'typeName':$("#typeName"+[int]).text(),
        'sortName':$("#sortName"+[int]).text(),
        'name1':$("#name1"+[int]).val(),
        'unit':$("#unit"+[int]).val(),
        'actualPrice':$("#actualPrice"+[int]).val(),
        'basePrice' :$("#baseQuote"+[int]).val(),
        'numerationPrice' :$("#numerationPrice"+[int]).val(),
        'workerRate':$("#workerRate"+[int]).val(),
        'managerRate':$("#managerRate"+[int]).val(),
        'profitRate':$("#profitRate"+[int]).val()
      });
    };

    return $http({
      method : 'POST',
      url : 'item/doSave.do', 
      headers : {
        'Content-Type' : undefined
      },
      transformRequest : function(data) {
        var formData = new FormData();
        
        formData.append("data", angular.toJson(data.data));
        
        for ( var f in data.file) {
          var items = data.file[f];
          for (var i in items) {
            formData.append(f, items[i]);
          }
        }
        return formData;
      },
      data : {
        data : $scope.data,
        file : $scope.file
      }
    }).then(function(response) {
      var data = response.data
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = "成功录入项目信息，返回项目列表";
        UrlUtil.autoJump(modalScope, function() {
          $window.location.href = UrlUtil.transform('item/itemList.do');
        });
      }
    });
  }

  $scope.goBack = function() {
    var apartyId = $("#apartyId").val();
    if (apartyId != "") {
      $window.location.href = UrlUtil.transform('aparty/apartyList.do');
    } else {
      $window.location.href = UrlUtil.transform('item/itemList.do');
    }
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


  var apartyId = $("#apartyId").val();
  if (apartyId != "") {
    $http.get('apartyContacts/queryContactsList.do', {
      params : {
        apartyId : apartyId,
        stamp : 'APARTY'
      }
    }).then(function(response) {
      $scope.gridOptions.data = $scope.data.contacts1List = response.data;
    });
  } else {
    $scope.gridOptions.data = $scope.data.contacts1List = [{}];
  }


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

  $scope.addRow = function() {
    $scope.gridOptions.data.push({});
  }

  var apartyId = $("#apartyId").val();
  if (apartyId != "") {
    $http.get('apartyContacts/queryContactsList.do', {
      params : {
        apartyId : apartyId,
        stamp : 'BPARTY'
      }
    }).then(function(response) {
      $scope.gridOptions.data = $scope.data.contacts2List = response.data;
    });
  } else {
    $scope.gridOptions.data = $scope.data.contacts2List = [{}];
  }


  $scope.deleteRow = function(row) {
    var index = $scope.gridOptions.data.indexOf(row.entity);
    $scope.gridOptions.data.splice(index, 1);
  };
}]);