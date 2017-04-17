<#include "/include/common.ftl" />
<style type="text/css">
</style>
<#include "/include/common-entry.ftl"/>
<script type="text/javascript" src="static/scripts/admin/adminAdd.js"></script>
</head>
<body>
<body>
  <#include "/include/body-begin.ftl" />

  <div ng-controller="AdminAddController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">新增管理员</div>
        <div class="panel-body">

          <div class="form-group"
            ng-class="{ 'has-error': form1.user.$invalid && (form1.$submitted || form1.user.$touched)}">
            <label class="control-label required col-md-2">用户名：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="user"
                ng-model="data.user" placeholder="必填项" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
            <label class="control-label required col-md-2">管理员名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" placeholder="必填项" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.email.$invalid && (form1.$submitted || form1.email.$touched)}">
            <label class="control-label required col-md-2">邮箱地址：</label>
            <div class="col-md-10">
              <input type="email" class="form-control" name="email"
                ng-model="data.email" placeholder="必填项" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.pwd.$invalid && (form1.$submitted || form1.pwd.$touched)}">
            <label class="control-label required col-md-2">登录密码：</label>
            <div class="col-md-10">
              <input type="password" class="form-control" name="pwd"
                ng-model="data.pwd" placeholder="必填项" required>
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
            <div class="col-md-8 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                ng-click="save();">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
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