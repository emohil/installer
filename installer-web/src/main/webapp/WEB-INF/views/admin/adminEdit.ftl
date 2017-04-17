<#include "/include/common.ftl" />
<style type="text/css">
</style>
<#include "/include/common-entry.ftl"/>
<script type="text/javascript" src="static/scripts/admin/adminEdit.js"></script>
</head>
<body>
<body ng-init="id='${id}'">

  <#include "/include/body-begin.ftl" />

  <div ng-controller="AdminEditController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">编辑管理员</div>
        <div class="panel-body">

          <div class="form-group">
            <label class="control-label col-md-2">用户名：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="user"
                ng-model="data.user" disabled>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.data.name1.$invalid && (form1.$submitted || form1.data.name1.$touched)}">
            <label class="control-label col-md-2">管理员名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.email.$invalid && (form1.$submitted || form1.email.$touched)}">
            <label class="control-label col-md-2">邮箱地址：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="email"
                ng-model="data.email" required>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-2">登录密码：</label>
            <div class="col-md-10">
              <input type="password" class="form-control" name="pwd"
                ng-model="data.pwd" placeholder="此密码框留空表示 不修改密码">
            </div>
          </div>

          <div class="form-group">
            <label class="control-label col-md-2">角色：</label>
            <div class="col-md-10">
              <div class="row">
                <#list roleList as role>
                <div class="col-md-2">
                  <label class="checkbox-inline"><input
                    type="checkbox" ng-model="data.roles.ID${role.id}">${role.name1}</label>
                </div>
                </#list>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-10 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();" ng-disabled="data.isSystem == 1"
                uib-tooltip="系统内置账号不允许操作"
                tooltip-enable="data.isSystem == 1">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
              </button>
              &nbsp; &nbsp; &nbsp;
              <button type="button" class="btn btn-danger"
                ng-click="doDelete();" ng-disabled="data.isSystem == 1"
                uib-tooltip="系统内置账号不允许操作"
                tooltip-enable="data.isSystem == 1">
                <span class="glyphicon glyphicon-trash"></span>&nbsp;删除
              </button>
              &nbsp; &nbsp; &nbsp;
              <button type="button" class="btn btn-success"
                ng-click="goBack();">
                <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
              </button>
            </div>
          </div>
        </div>
      </div>
    </form>
    <#include "/include/body-end.ftl" />
</body>
</html>