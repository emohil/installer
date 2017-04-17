var app = angular.module('app',// 
['ui.bootstrap']);

app.controller('ApartyController', ['$scope', '$http', '$window',//
function($scope, $http, $window) {

  $scope.doTypeaheadApartys = function(q) {
    $scope.apartyId = '';
    return $http.post('aparty/doTypeahead.do', {
      q : q
    }).then(function(response) {
      return response.data;
    });
  };

  $scope.onTypeaheadApartySelect = function($item, $model, $label) {
    $scope.apartyId = $item.value;
  };
}]);