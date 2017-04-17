var app = angular.module('app',// 
['ui.grid', 'ui.grid.edit', 'ui.grid.cellNav', 'ui.grid.validate', 'ui.bootstrap', 'ifu.util']);

app.controller('CityEditController', ['$scope', '$http', '$window', 'UrlUtil',//
function($scope, $http, $window, UrlUtil) {

  $scope.contactsDatas = {};
  
  var id = $scope.data.id;

  // load
  $http.get('city/load.do', {
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
    var provId = $scope.data.provId;
    $http.post('city/update.do', $scope.data).then(function() {
      $scope.goBack();
    });
  }
  
  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('city/cityList.do?sf.provId='+$scope.data.provId);
  }
  
}]);

