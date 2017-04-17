<#include "/include/common.ftl" />
<style type="text/css">
</style>
<#include "/include/common-entry.ftl"/>
<script type="text/javascript" src="static/scripts/role/roleEdit.js"></script>
</head>
<body>
<body ng-init="id='${id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="RoleEditController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">编辑管理角色</div>
        <div class="panel-body">

          <div class="form-group">
            <label class="control-label required col-md-2">角色代码：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="code1"
                ng-model="data.code1" disabled>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
            <label class="control-label required col-md-2">角色名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.name1.$invalid"
                ng-disabled="data.isSystem == 1" required>
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
                    type="checkbox" ng-model="data.auth.${auth.code1}"
                    ng-disabled="data.isSystem == 1">${auth.name1}</label>
                </div>
                </#list>
              </div>
            </div>
          </div>
          </#list>
          <div class="row">
            <div class="col-md-10 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();" ng-disabled="data.isSystem == 1"
                uib-tooltip="系统内置角色不允许操作"
                tooltip-enable="data.isSystem == 1">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
              </button>
              &nbsp; &nbsp; &nbsp;
              <button type="submit" class="btn btn-danger"
                ng-click="doDelete();" ng-disabled="data.isSystem == 1"
                uib-tooltip="系统内置角色不允许操作"
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
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>