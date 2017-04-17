var app = angular.module('app', ['treeGrid','ifu.util'])//
app.controller('ScnodeController', ['$scope', function($scope) {

}]);

app.controller('TreeGridController', ['$scope', '$http', '$window', 'UrlUtil',
                                      function($scope, $http, $window, UrlUtil) {

  $scope.tree = {};
  $scope.tree_data = [];

  $scope.expanding_property = {
      field : 'name1',
      displayName : '名称'
  };
  $scope.col_defs = [{
    width : '100px',
    field : 'stepTypeDisp',
    displayName : '类型'
  }, {
    width : '100px',
    field : 'stepStatusDisp',
    displayName : '状态'
  }, {
    width : '200px',
    field : 'op',
    displayName : '操作',
    cellTemplate : 
      "<span ng-if=\"row.branch['iEList'].length > 0 && row.branch['result'] != 'SUSPEND'\">" + //
      "  <a class=\"btn btn-warning btn-xs\" " + //
      "    href=\"indentExc/indentExcList.do?indentNodeId={{row.branch['id']}}\">" + //
      "      <span class=\"glyphicon glyphicon-eye-open\"></span>&nbsp;查看异常</a>" + //
      "</span>" + //
      "<span ng-if=\"row.branch['iEList'].length > 0 && row.branch['result'] == 'SUSPEND'\">" + //
      "  <a class=\"btn btn-warning btn-xs\" " + //
      "    href=\"indentExc/indentExcList.do?indentNodeId={{row.branch['id']}}\">" + //
      "      <span class=\"glyphicon glyphicon-exclamation-sign\"></span>&nbsp;暂停中</a>" + //
      "</span>" + //
      "<span ng-if=\"row.branch['iEList'].length == 0&&row.level == 1\">无异常信息</span>" + //
      
      "<span ng-if=\"row.branch['stepType'] == 'UPLOAD' && row.branch['stepStatus'] == 'FINISH'\">" +
      " <a class=\"btn btn-primary btn-xs\" " +
      "   href=\"indentNodeStep/findImg.do?id={{row.branch['id']}}\">" + //
      "      <span class=\"glyphicon glyphicon-eye-open\"></span>&nbsp;查看照片</a>" +
      "</span>"
  }];

  $http.get('indentNode/loadTreeDatas.do?id='+$scope.data.id).then(function(response) {
    $scope.tree_data = response.data;
  });

  $scope.goBack = function() {
    $window.location.href = UrlUtil.transform('indent/indentList.do');
  }
  
  $scope.lookException = function() {
    $window.location.href = UrlUtil.transform('indentExc/indentExcList.do?indentId='+$scope.data.id);
  }
  
}]);