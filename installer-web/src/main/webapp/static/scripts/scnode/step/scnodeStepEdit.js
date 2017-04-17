var app = angular.module('app', ['ui.bootstrap', 'ifu.template', 'ifu.form', 'ifu.util']);

app.controller('ScnodeStepController', ['$scope', '$http', '$window', '$uibModal', 'UrlUtil',//
function($scope, $http, $window, $uibModal, UrlUtil) {

  // load
  $http.get('scnodeStep/load.do', {
    params : {
      id : $scope.id
    }
  }).then(function(response) {
    $scope.data = response.data;
  });

  $scope.doSave = function() {

    if (!$scope.form1.$valid) {
      return false;
    }
    $http.post('scnodeStep/update.do', $scope.data).then(function() {
      $scope.goBack();
    });
  }

  $scope.doDelete = function() {

    var id = $scope.data.id;

    var modalScope = $scope.$new(true);
    modalScope.message = "您确认要删除该步骤吗？这将会同时删除该步骤下的子项! ";

    $uibModal.open({
      templateUrl : 'template/modal/delete.html',
      scope : modalScope
    }).result.then(function() {
      $http.get('scnodeStep/delete.do', {
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