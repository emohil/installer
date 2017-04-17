var app = angular.module('app', ['ui.bootstrap', 'ifu.template', 'ifu.form', 'ifu.util']);

app.controller('SctypeContentController', ['$scope', '$http', '$window', '$uibModal', 'UrlUtil',//
function($scope, $http, $window, $uibModal, UrlUtil) {

  // load
  $http.get('scnodeStepItem/load.do', {
    params : {
      id : $scope.id
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  // load files
  $scope.loadSketchFiles = function() {
    $http.get('scnodeStepItem/loadSketchFiles.do', {
      params : {
        id : $scope.id
      }
    }).then(function(data) {
      $scope.sketchFiles = data.data;
    });
  }
  $scope.loadSketchFiles();

  $scope.removeFile = function(fileid) {
    $uibModal.open({
      templateUrl : 'template/modal/delete.html'
    }).result.then(function() {
      $http.get('fs/delete.do', {
        params : {
          fileid : fileid
        }
      }).then($scope.loadSketchFiles);
    });
  }

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }

    $http({
      method : 'POST',
      url : 'scnodeStepItem/doUpdate.do',
      headers : {
        'Content-Type' : undefined
      },
      transformRequest : function(data) {
        var formData = new FormData();

        formData.append("data", angular.toJson(data.data));

        for ( var f in data.file) {
          var items = data.file[f];
          for ( var i in items) {
            formData.append(f, items[i]);
          }
        }
        return formData;
      },
      data : {
        data : $scope.data,
        file : $scope.file
      }
    }).then(function() {
      $scope.goBack();
    });
  }

  $scope.doDelete = function() {

    var id = $scope.data.id;

    $uibModal.open({
      templateUrl : 'template/modal/delete.html'
    }).result.then(function() {
      $http.get('scnodeStepItem/delete.do', {
        params : {
          id : id
        }
      }).then($scope.goBack);
    });
  };

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('scnode/scnodeTree.do');
  }
}]);