var app = angular.module('app', ['treeGrid', 'ifu.util'])//
app.controller('SctypeController', ['$scope', function($scope) {

}]);

app.controller('TreeGridController', ['$scope', '$http', '$window', 'UrlUtil',
    function($scope, $http, $window, UrlUtil) {

      $scope.addSctype = function() {
        $window.location.href = UrlUtil.transform('sctype/sctypeAdd.do');
      }

      $scope.tree = {};
      $scope.tree_data = [];

      $scope.expanding_property = {
        width : '250px',
        field : 'code1',
        displayName : '代码'
      };
      $scope.col_defs = [{
        field : 'name1',
        displayName : '名称'
      }, {
        width : '100px',
        field : 'unitDisp',
        displayName : '单位'
      }, {
        width : '100px',
        field : 'baseQuote',
        displayName : '基础报价'
      }, {
        width : '188px',
        field : 'op',
        displayName : '操作',
        cellTemplate : //
        "<span ng-if=\"row.level == 1\">" + //
        "  <a class=\"btn btn-success btn-xs\" " + //
        "    href=\"sctype/sctypeEdit.do?id={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-edit\"></span>&nbsp;类型</a>" + //
        "  &nbsp;<a class=\"btn btn-primary btn-xs\" " + //
        "    href=\"sctypeSort/sctypeSortAdd.do?sctypeId={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-plus\"></span>&nbsp;类别</a>" + //
        "  &nbsp;<a class=\"btn btn-warning btn-xs\" " + //
        "    href=\"sctypeNode/sctypeNodeEdit.do?sctypeId={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-th-list\"></span>&nbsp;节点</a>" + //
        "</span>" + //
        "<span ng-if=\"row.level == 2\">" + //
        "  <a class=\"btn btn-success btn-xs\" " + //
        "    href=\"sctypeSort/sctypeSortEdit.do?id={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-edit\"></span>&nbsp;类别</a>" + //
        "  &nbsp;<a class=\"btn btn-primary btn-xs\" " + //
        "    href=\"sctypeContent/sctypeContentAdd.do?sctypeSortId={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-plus\"></span>&nbsp;内容</a>" + //
        "</span>" + //
        "<span ng-if=\"row.level == 3\">" + //
        "  <a class=\"btn btn-success btn-xs\" " + //
        "    href=\"sctypeContent/sctypeContentEdit.do?id={{row.branch['id']}}\">" + //
        "      <span class=\"glyphicon glyphicon-edit\"></span>&nbsp;内容</a>" + //
        "</span>"
      }];

      $http.get('sctype/loadTreeDatas.do').then(function(response) {
        $scope.tree_data = response.data;
      });

      // $scope.tree_data = [{
      // id : '1',
      // code1 : 'TYPE001',
      // name1 : '测量',
      // children : [{
      // id : '2',
      // code1 : 'CE',
      // name1 : '内门类',
      // children : [{
      // id : '3',
      // code1 : 'agag',
      // name1 : '普通橱柜类',
      // unit : '延米',
      // baseQuote : 12.93
      // }, {
      // code1 : 'dee',
      // name1 : '高档柜体',
      // unit : '延米',
      // baseQuote : 11.93
      // }]
      // }]
      // }, {
      // code1 : 'TYPE002',
      // name1 : '送装'
      // }, {
      // code1 : 'TYPE003',
      // name1 : '纯安装'
      // }];

    }]);