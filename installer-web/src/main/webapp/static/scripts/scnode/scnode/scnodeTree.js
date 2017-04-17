var app = angular.module('app', ['treeGrid', 'ifu.util'])//
app.controller('ScnodeController', ['$scope', function($scope) {

}]);

app.controller('TreeGridController', ['$scope', '$http', '$window', 'UrlUtil',
    function($scope, $http, $window, UrlUtil) {

      $scope.addScnode = function() {
        $window.location.href = UrlUtil.transform('scnode/scnodeAdd.do');
      }

      $scope.tree = {};
      $scope.tree_data = [];

      $scope.expanding_property = {
        width : '200px',
        field : 'code1',
        displayName : '代码'
      };
      $scope.col_defs = [{
        field : 'name1',
        displayName : '名称'
      }, {
        width : '100px',
        field : 'sortIdDisp',
        displayName : '服务类别'
      }, {
        width : '100px',
        field : 'typeDisp',
        displayName : '节点类型'
      }, {
        width : '40px',
        field : 'orders',
        displayName : '序号'
      }, {
        width : '125px',
        field : 'op',
        displayName : '操作',
        cellTemplate : //
        "<span ng-if=\"row.level == 1\">" + //
        "  <a class=\"btn btn-success btn-xs\" " + //
        "    href=\"scnode/scnodeEdit.do?id={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-edit\"></span>&nbsp;节点</a>" + //
        "  &nbsp;<a class=\"btn btn-primary btn-xs\" " + //
        "    href=\"scnodeStep/scnodeStepAdd.do?scnodeId={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-plus\"></span>&nbsp;步骤</a>" + //
        "</span>" + //
        "<span ng-if=\"row.level == 2\">" + //
        "  <a class=\"btn btn-success btn-xs\" " + //
        "    href=\"scnodeStep/scnodeStepEdit.do?id={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-edit\"></span>&nbsp;步骤</a>" + //
        "  &nbsp;<a class=\"btn btn-primary btn-xs\" " + //
        "    href=\"scnodeStepItem/scnodeStepItemAdd.do?scnodeStepId={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-plus\"></span>&nbsp;子项</a>" + //
        "</span>" + //
        "<span ng-if=\"row.level == 3\">" + //
        "  <a class=\"btn btn-success btn-xs\" " + //
        "    href=\"scnodeStepItem/scnodeStepItemEdit.do?id={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-edit\"></span>&nbsp;子项</a>" + //
        "</span>"
      }];

      $http.get('scnode/loadTreeDatas.do').then(function(response) {
        $scope.tree_data = response.data;
      });
    }]);