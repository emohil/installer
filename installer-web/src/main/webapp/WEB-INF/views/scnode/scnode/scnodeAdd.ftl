<#include "/include/common.ftl" />
<!-- placeholder -->
<#include "/include/common-entry.ftl" />
<script src="static/scripts/scnode/scnode/scnodeAdd.js"></script>

</head>

<body>
  <#include "/include/body-begin.ftl" />

  <div ng-controller="ScnodeController">

    <div class="row">
      <div class="col-md-12">
        <ol class="breadcrumb">
          <li><a href="homepage.do"><span
              class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
          <li><a><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;系统设置</a></li>
          <li><a href="scnode/scnodeTree.do"><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;服务节点</a></li>
          <li class="active"><span
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;添加服务节点</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>

      <div class="panel panel-default">
        <div class="panel-heading">服务节点添加</div>
        <div class="panel-body">
          <div class="form-group"
            ng-class="{'has-error' : form1.code1.$invalid && (form1.$submitted || form1.code1.$touched)}">
            <label class="control-label required col-md-4">服务节点编码：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="code1"
                ng-model="data.code1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.$invalid" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
            <label class="control-label required col-md-4">服务节点名称：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.name1.$invalid" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.orders.$invalid && (form1.$submitted || form1.orders.$touched)}">
            <label class="control-label required col-md-4">服务节点序号：</label>
            <div class="col-md-4">
              <input type="text" positive-integer class="form-control"
                name="orders" ng-model="data.orders"
                ng-init="data.orders=1" required>
            </div>
          </div>
          <div class="row">
            <div class="col-md-8 col-md-offset-4">
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();">
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