<#include "/include/common.ftl" />
<!-- placeholder -->
<style>
.row {
  border: solid 1px medium;
}
</style>
<#include "/include/common-entry.ftl" />
<script src="static/scripts/sctype/sctype/sctypeAdd.js"></script>

</head>

<body>
  <#include "/include/body-begin.ftl" />

  <div ng-controller="SctypeController">

    <div class="row">
      <div class="col-md-12">
        <ol class="breadcrumb">
          <li><a href="homepage.do"><span
              class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
          <li><a><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;系统设置</a></li>
          <li><a href="sctype/sctypeTree.do"><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;服务类型</a></li>
          <li class="active"><span
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;添加服务类型</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>

      <div class="panel panel-default">
        <div class="panel-heading">
          <div class="panel-title">服务类型添加</div>
        </div>
        <div class="panel-body">
          <div class="form-group"
            ng-class="{'has-error' : form1.code1.$invalid && (form1.$submitted || form1.code1.$touched)}">
            <label
              class="control-label required col-md-2 col-md-offset-2">服务类型编码：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="code1"
                ng-model="data.code1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.$invalid" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
            <label
              class="control-label required col-md-2 col-md-offset-2">服务类型名称：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.name1.$invalid" required>
            </div>
            <div class="col-md-4"></div>
          </div>
          <div class="row">
            <div class="col-md-8 col-md-offset-4">
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
              </button>
              &nbsp;&nbsp;&nbsp;
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