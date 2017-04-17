'use strict';

angular.module('ifu.cityselect2', [])//

.directive('cityselect2', ['$http', function($http) {

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

      $http.get('city/cityData.do').then(function(response) {

        var CityData = response.data;

        var selects = element.find('select'), // 
        pSel = selects[0], // 
        cSel = selects[1], // 
        pEle = angular.element(pSel), // 
        cEle = angular.element(cSel); // 

        $scope.provs = buildOptionDatas(CityData);

        $scope.$watch(pEle.attr('ng-model'), function(newVal) {
          // if (!newVal) {
          // $scope.cities = undefined;
          // $scope.dists = undefined;
          // return;
          // }

          var pId = pSel.selectedIndex;
          var selectedProv;
          while ((selectedProv = CityData[pId]) && selectedProv.v != newVal) {
            pId++;
          }
          if (selectedProv && selectedProv.c) {
            $scope.cities = buildOptionDatas(selectedProv.c);
          } else {
            $scope.cities = undefined;
          }
        });

        $scope.$watch(cEle.attr('ng-model'), function(newVal) {
          if (!newVal) {
            cSel.selectedIndex = 1;
            $scope.dists = undefined;
          }

          var pId = pSel.selectedIndex;
          var cId = cSel.selectedIndex;

          // 当前所选省下面的城市列表
          var cities = CityData[pId].c;

          // 采用selectedIndex定位默认选择项 在当前环境不可靠，这里添加 值比对
          var selectedCity;
          while ((selectedCity = cities[cId]) && selectedCity.v != newVal) {
            cId++;
          }
          cSel.selectedIndex = cId;

          if (selectedCity && selectedCity.c) {
            $scope.dists = buildOptionDatas(selectedCity.c);
          } else {
            $scope.dists = undefined;
          }
        });

      });
    }
  };
}]);