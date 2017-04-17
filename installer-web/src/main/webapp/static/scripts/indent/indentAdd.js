var app = angular.module('app',// 
    ['ui.bootstrap', 'ifu.cityselect2','ifu.form', 'ifu.util', 'ifu.workerselect']);

app.controller('IndentController', ['$scope', '$http', '$window', 'UrlUtil', '$uibModal',
function($scope, $http, $window, UrlUtil, $uibModal) {

  $scope.dataSort = '';
  
  $scope.data = {};

  $scope.planDate = {
      opened : false
  };

  $scope.openPlanDate = function() {
    $scope.planDate.opened = true;
  }

  $scope.save = function() {
  
    if (!$scope.form1.$valid) {
      var modalScope = $scope.$new(true);
      modalScope.title = "信息不完整";
      modalScope.message = "当前表单输入信息不完整，请补充完整后再保存！";
      $uibModal.open({
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      }); 
      return false;
    }
    
    if ($scope.data.serveType != 'C' && ($scope.file == null || $scope.file.imgFiles.length == 0)) {
      var modalScope = $scope.$new(true);
      modalScope.title = "图纸不正确";
      modalScope.message = "请上传安装图纸！";
      $uibModal.open({
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      }); 
      return false;
    }
    
    if ($scope.data.indentAssignSelected == true &&
        (($scope.data.managerId == null || $scope.data.managerId.length < 0) ||
        ($scope.data.workerId == null || $scope.data.workerId.length < 0))) {
      var modalScope = $scope.$new(true);
      modalScope.title = "指派工匠";
      modalScope.message = "请指派工匠！";
      $uibModal.open({
        templateUrl : 'template/modal/alert.html',
        scope : modalScope
      }); 
      return false;
    }
    
    return $http({
      method : 'POST',
      url : 'indent/doSave.do', 
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
      var data = response.data;
      if (data.success) {
        var modalScope = $scope.$new(true);
        modalScope.message = data.err_msg;
        UrlUtil.autoJump(modalScope, $scope.goList);
      } else {
        var modalScope = $scope.$new(true);
        modalScope.title = "添加订单失败";
        modalScope.message = data.err_msg;
        $uibModal.open({
          templateUrl : 'template/modal/alert.html',
          scope : modalScope
        }); 
      }
    });
  
  }
  
  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('item/itemList.do');
  }
  $scope.goList = function() {
    $window.location.href = UrlUtil.transform('indent/indentList.do');
  }

  $scope.doTypeaheadApartys = function(q) {
    return $http.post('aparty/doTypeahead.do', {
      q : q
    }).then(function(response) {
      return response.data;
    });
  };

  $scope.onTypeaheadApartySelect = function($item, $model, $label) {
    $scope.data.apartyId = $item.value;
  };

  $scope.doTypeaheadItems = function(q) {
    return $http.post('item/doTypeahead.do', {
      id: $scope.data.apartyId,
      q : q
    }).then(function(response) {
      return response.data;
    });
  };

  $scope.onTypeaheadItemSelect = function($item, $model, $label) {
    $scope.data.itemId = $item.value;
    $scope.data.apartyIdDisp = $item.value2;
    $window.location.href = UrlUtil.transform('indent/indentAdd.do?id='+ $item.value);
  };
  
  $scope.changeTarget = function($event) {
    console.log($scope.data);
    $window.location.href = UrlUtil.transform('indent/indentAdd.do?id='+ $scope.data.itemId + '&serveType=' + $scope.data.serveType);
  }
  
  $scope.judge_drawing_top_limit= function(id) {
    $scope.dataSort += id + "_";
    }
}]);




