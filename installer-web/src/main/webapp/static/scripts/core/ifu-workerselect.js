'use strict';

angular.module('ifu.workerselect', [])//

.directive('workerselect', ['$http', function($http) {

  var emptyOption = {
    n : '--请选择--',
    v : ''
  };

  var buildOptionDatas = function(ds) {
    return ds;
    /*
     * var options = [emptyOption]; if (!ds) { return options; }
     * angular.forEach(ds, function(o) { options.push({ v : o.v, n : o.n }); });
     * return options;
     */
  }

  return {
    restrict : 'A',
    template : '',
    scope : true,
    link : function($scope, element, attrs) {

      $http.get('manager/managerData.do').then(function(response) {
        var WorkerData = response.data;

        var selects = element.find('select'), // 
        mSel = selects[0], // 
        wSel = selects[1], // 
        mEle = angular.element(mSel), // 
        wEle = angular.element(wSel); // 

        $scope.managers = buildOptionDatas(WorkerData);

        $scope.$watch(mEle.attr('ng-model'), function(newVal) {
          
          $http.get('worker/workerData.do',{
            params : {
              managerId : $scope.data.managerId,
              serveType : $scope.data.serveType,
              regionDist : $scope.data.regionDist,
              sorts : $scope.dataSort
            }
          }).then(function(response) {
            //动态加载 符合条件的工匠信息
            var WorkerDate = response.data;
            $scope.workers = buildOptionDatas(WorkerDate);
          });
        });
      });
    }
  };
}]);