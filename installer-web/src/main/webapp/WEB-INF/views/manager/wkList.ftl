<#include "/include/common.ftl" />
<title>工匠管理查询</title>
<#include "/include/common-list.ftl" />
<script src="static/scripts/account/wkList.js"></script>
</head>
<body ng-init="data.id='${id}'">
  <#include "/include/body-begin.ftl" />
  <div ng-controller="WkListController">


    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">工人列表</div>


      <div class="panel panel-default">
        <table class="table table-bordered table-striped">
          <thead>
            <tr>
              <th>姓名</th>
              <th>手机号</th>
              <th>备注</th>
              <th>时间</th>
              <th>状态</th>
            </tr>
          </thead>
          <tbody ng-repeat="data in rows">
            <tr>
              <td>{{data.name1}}</td>
              <td>{{data.mobile}}</td>
              <td>{{data.desc1}}</td>
              <td>{{data.applyDate}}</td>
              <td>{{data.statusDisp}}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="form-group">
        <div class="col-md-12">
          <button type="button" class="btn btn-success pull-right"
            ng-click="goBack();">
            <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
          </button>
        </div>
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>