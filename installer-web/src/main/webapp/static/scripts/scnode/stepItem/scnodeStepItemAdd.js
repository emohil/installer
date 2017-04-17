var app = angular.module('app', ['ui.bootstrap', 'ifu.form', 'ifu.util']);

app.controller('SctypeContentController', ['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  $scope.data = {
    scnodeStepId : $scope.scnodeStepId
  };

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }

    $http({
      method : 'POST',
      url : 'scnodeStepItem/doSave.do',
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

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('scnode/scnodeTree.do');
  }
}]);