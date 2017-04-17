<#include "/include/common.ftl" />
<!-- placeholder -->
<#include "/include/common-entry.ftl" />
<script src="static/scripts/scnode/step/scnodeStepAdd.js"></script>

</head>

<body ng-init="scnodeId='${scnode.id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="ScnodeStepController">

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
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;添加服务步驟</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">服务节点步骤添加</div>
        <div class="panel-body">
          <div class="form-group">
            <label class="control-label col-md-2">服务节点代码：</label>
            <div class="col-md-10">
              <input type="text" class="form-control"
                value="${scnode.code1}" disabled>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-2">服务节点名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control"
                value="${scnode.name1}" disabled>
            </div>
          </div>
          <div class="row divider"></div>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.stepType.$invalid && (form1.$submitted || form1.stepType.$touched)}">
                <label class="control-label required col-md-4">节点步骤类型：</label>
                <div class="col-md-8">
                  <select type="text" class="form-control"
                    name="stepType" ng-model="data.stepType"
                    uib-tooltip="此项为必选项"
                    tooltip-enable="form1.stepType.$invalid" required>
                    <#list stepTypeList as s>
                    <option value="${s.value}">${s.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.orders.$invalid && (form1.$submitted || form1.orders.$touched)}">
                <label class="control-label required col-md-4">节点步骤序号：</label>
                <div class="col-md-8">
                  <input type="text" positive-integer
                    class="form-control" name="orders"
                    ng-model="data.orders" ng-init="data.orders=1"
                    required>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">节点步骤类别：</label>
                <div class="col-md-8">
                  <select type="text" class="form-control" name="sortId"
                    ng-model="data.sortId"> <#list sortList as
                    s>
                    <option value="${s.value}">${s.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
          </div>

          <div class="form-group"
            ng-class="{'has-error' : form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
            <label class="control-label required col-md-2">节点步骤名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.name1.$invalid" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.desc1.$invalid && (form1.$submitted || form1.desc1.$touched)}">
            <label class="control-label required col-md-2">节点步骤描述：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control" name="desc1"
                ng-model="data.desc1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.$invalid" required></textarea>
            </div>
          </div>
          <div class="row">
            <div class="col-md-8 col-md-offset-2">
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