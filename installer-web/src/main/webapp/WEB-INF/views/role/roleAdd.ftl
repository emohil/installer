<#include "/include/common.ftl" />
<style type="text/css">
</style>
<#include "/include/common-entry.ftl"/>
<script type="text/javascript" src="static/scripts/role/roleAdd.js"></script>
</head>
<body>
<body>
  <#include "/include/body-begin.ftl" />
  <div ng-controller="RoleAddController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">新增管理角色</div>
        <div class="panel-body">

          <div class="form-group"
            ng-class="{ 'has-error': form1.code1.$invalid && (form1.$submitted || form1.code1.$touched)}">
            <label class="control-label required col-md-2">角色代码：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="code1"
                ng-model="data.code1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.code1.$invalid" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
            <label class="control-label required col-md-2">角色名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.name1.$invalid" required>
            </div>
          </div>
        </div>
      </div>

      <div class="panel panel-default">
        <div class="panel-heading">角色权限</div>
        <div class="panel-body">

          <#list authGroupList as group>

          <div class="form-group">
            <label class="control-label col-md-2">${group.name1}：</label>
            <div class="col-md-10">
              <div class="row">

                <#list group.authList as auth>
                <div class="col-md-2">
                  <label class="checkbox-inline"><input
                    type="checkbox" ng-model="data.auth.${auth.code1}">${auth.name1}</label>
                </div>
                </#list>
              </div>
            </div>
          </div>
          </#list>
          <div class="row">
            <div class="col-md-10 col-md-offset-2">
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
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>