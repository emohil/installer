<#include "/include/common.ftl" />
<title>角色管理</title>
<#include "/include/common-list.ftl" />
<script src="static/scripts/role/roleList.js"></script>
</head>
<body>

  <#include "/include/body-begin.ftl" />

  <div ng-controller="RoleListController">

    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">角色管理</div>
      <div class="panel-body">

        <div class="row">

          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">角色代码</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.code1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">角色名称</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.name1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 pull-right">
            <div class="btn-group pull-right">
              <button class="btn btn-warning" ng-click="query()">
                <span class="glyphicon glyphicon-search"></span>&nbsp;筛选
              </button>
              <a class="btn btn-primary" href="role/roleAdd.do"><span
                class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="panel panel-default">
      <table class="table table-bordered table-striped">
        <thead>
          <tr>
            <th>角色代码</th>
            <th>角色名称</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td>{{data.code1}}</td>
            <td><a href="role/roleEdit.do?id={{data.id}}">{{data.name1}}</a></td>
            <td><a href="role/roleEdit.do?id={{data.id}}"
              class="btn btn-primary btn-xs"><span
                class="glyphicon glyphicon-edit"></span>&nbsp;编辑</a>&nbsp;</td>
          </tr>
        </tbody>
      </table>

      <div class="panel-footer">
        <!--  -->
        <#include "/include/pagination.ftl" />
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>