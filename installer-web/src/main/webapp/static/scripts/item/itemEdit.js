var app = angular.module('app',// 
    ['ui.grid', 'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.bootstrap', //
     'ifu.cityselect2', 'ifu.form', 'ifu.template', 'ifu.util']);

app.controller('ItemController', ['$scope', '$http', '$window', '$uibModal', 'UrlUtil',//
                                  function($scope, $http, $window, $uibModal, UrlUtil) {

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

  $scope.contactsDatas = {};

  var count = 0;

  // load
  $http.get('item/load.do', {
    params : {
      id : $scope.data.id
    }
  }).then(function(response) {
    $scope.data = response.data;

    $scope.data.beginDate = Date.parse($scope.data.beginDate.replace());
    $scope.data.overDate = Date.parse($scope.data.overDate.replace());


    var itemPriceList1 = $scope.data.itemPriceList;
    var index = 0;
    for (var int = 0; int < itemPriceList1.length; int++) {
      var itemPrice = itemPriceList1[int];
      $("#offer tbody").append('<tr><td id="typeName'+index+'">'+itemPrice.typeName
          +'</td><td id="sortName'+index+'">'+itemPrice.sortName
          +'<input type="hidden" id="serveContentId'+index+'" value="'+itemPrice.serveContentId+'"/>'
          +'</td><td><input type="hidden" id="name1'+index+'" value="'+itemPrice.name1+'">'+itemPrice.name1
          +'</td><td><input type="hidden" id="unit'+index+'" value="'+itemPrice.unit+'">'+itemPrice.unitDisp
          +'</td><td>￥<input type="text" id="actualPrice'+index+'" value="'+itemPrice.actualPrice
          +'"></td><td><input type="text" id="workerRate'+index+'" onblur="jian('+index+');" value="'+itemPrice.workerRate+'"/>%'
          +'</td><td><input type="text" id="managerRate'+index+'" onblur="jian('+index+');" value="'+itemPrice.managerRate+'"/>%'
          +'</td><td><input type="text" id="profitRate'+index+'" disabled value="'+itemPrice.profitRate+'"/>%</td></tr>');
      $("#offer tbody input").css({'width':'100px','border':'1px solid #ccc'});
      index++;
    }
    count = index;

  });
  
  //load files
  $http.get('item/loadContractFiles.do', {
    params : {
      id : $scope.data.id
    }
  }).then(function(data) {
    $scope.contractFiles = data.data;
  });
  
  
  var serveSortIds = $("#serveSortIds").val();
  var ids = serveSortIds.substring(1, serveSortIds.length-1)
  var serveSortIdss = ids.split(", ");

  angular.forEach(serveSortIdss, function(data) {
    $("[type=checkbox]").each(function (){
      if($(this).val() == data) {
        $(this).attr("checked", true);
      }
    })
  })

  $scope.add = function() {
    
    if (!$scope.form1.regionProv.$valid) {
      alert("请先选择服务地址");
      return false;
    }

    $scope.data.itemPriceList2 = [];

    for (var int = 0; int < count; int++) {
      $scope.data.itemPriceList2.push({'serveContentId':$("#serveContentId"+[int]).val(),
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
      $("#offer tbody").empty();
      var index = 0;
      for (var i = 0; i < response.data.length; i++) {
        var type = response.data[i];
        for (var j = 0; j < type.sortList.length; j++) {
          var sort = type.sortList[j];
          for (var k = 0; k < sort.contentList.length; k++) {
            var content = sort.contentList[k];
            var flag = false;
            angular.forEach($scope.data.itemPriceList2, function(data) {
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
      count = index;
      $("#offer tbody input").css({'width':'100px','border':'1px solid #ccc'});

    });
  }

  $scope.save = function() {
    if (!$scope.form1.$valid) {
      return false;
    }
    $scope.data.itemPriceList = [];
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

    for ( var d in $scope.contactsDatas) {
      $scope.data[d] = $scope.contactsDatas[d];
    }
    
    $http({
      method : 'POST',
      url : 'item/doUpdate.do', 
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
        UrlUtil.autoJump(modalScope, $scope.goBack);
      }
    });
  }

  $scope.doDelete = function() {


    var id = $scope.data.id;

    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要删除该项目信息吗？如果该项目已发布订单，将不能删除! ";

    $uibModal.open({
      templateUrl : 'template/modal/delete.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('item/deleteItem.do', {
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
  
  $scope.delPact = function(id) {

    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要删除该合同信息吗？";

    $uibModal.open({
      templateUrl : 'template/modal/delete.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('item/delPact.do', {
        params : {
          id : id
        }
      }).then(function(response) {
        var data = response.data;
        if (data.success) {
          alert("成功删除该合同信息！");
          $http.get('item/loadContractFiles.do', {
            params : {
              id : $scope.data.id
            }
          }).then(function(data) {
            $scope.contractFiles = data.data;
          });
        } else {
          alert("删除失败");
        }

      });
    });

  }

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('item/itemList.do');
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

  $http.get('itemContacts/queryContactsList.do', {
    params : {
      itemId : $scope.data.id,
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

  $http.get('itemContacts/queryContactsList.do', {
    params : {
      itemId : $scope.data.id,
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