<#include "/include/common.ftl" />
<!-- placeholder -->
<#include "/include/common-entry.ftl" />
<script src="static/scripts/scnode/stepItem/scnodeStepItemAdd.js"></script>

</head>

<body ng-init="scnodeStepId='${scnodeStep.id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="SctypeContentController">
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
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;添加服务步驟子项</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">添加服务步驟子项</div>
        <div class="panel-body">
          <div class="form-group">
            <label for="name1" class="control-label col-md-2">服务步骤名称：</label>
            <div class="col-md-10">
              <input type="text" disabled class="form-control"
                value="${scnodeStep.name1}" />
            </div>
          </div>
          <div class="form-group">
            <label for="addRate" class="control-label col-md-2">服务步骤描述：</label>
            <div class="col-md-10">
              <textarea class="form-control" disabled>${scnodeStep.desc1}</textarea>
            </div>
          </div>

          <div class="row divider"></div>

          <div class="row">
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
            <#if scnodeStep.stepType=='CONFIRM'>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.itemType.$invalid && (form1.$submitted || form1.itemType.$touched)}">
                <label class="control-label required col-md-4">步骤确认类型：</label>
                <div class="col-md-8">
                  <select class="form-control" name="itemType"
                    ng-model="data.itemType" uib-tooltip="此项为必选项"
                    tooltip-enable="form1.itemType.$invalid" required>
                    <#list confirmTypeList as s>
                    <option value="${s.value}">${s.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
            </#if>
            <!--  -->
            <#if scnodeStep.stepType=='UPLOAD'>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.itemType.$invalid && (form1.$submitted || form1.itemType.$touched)}">
                <label class="control-label required col-md-4">最少图片限制：</label>
                <div class="col-md-8">
                  <input type="text" positive-integer
                    class="form-control" name="minPhoto"
                    ng-model="data.minPhoto" ng-init="data.minPhoto=1"
                    required>
                </div>
              </div>
            </div>
            </#if>
          </div>

          <div class="form-group"
            ng-class="{'has-error' : form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
            <label class="control-label required col-md-2">服务节点名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="name1"
                ng-model="data.name1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.name1.$invalid" required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.desc1.$invalid && (form1.$submitted || form1.desc1.$touched)}">
            <label class="control-label required col-md-2">步骤子项描述：</label>
            <div class="col-md-10">
              <textarea class="form-control" name="desc1"
                ng-model="data.desc1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.desc1.$invalid" required></textarea>
            </div>
          </div>

          <div class="form-group">
            <div class="col-md-2 col-md-offset-2">
              <label class="checkbox-inline"><input
                type="checkbox" ng-model="data.isSketch"
                ng-true-value="1" ng-false-value="0">启用示意图</label>
            </div>
            <div class="col-md-8">
              <input type="file" name="sketchFiles"
                file-model="file.sketchFiles" accept="image/*" multiple
                ng-disabled="!data.isSketch" />
            </div>
          </div>
          <div class="row" ng-if="data.isSketch">
            <div class="col-md-10 col-md-offset-2">
              <div class="row" ng-repeat="f in file.sketchFiles">
                <div class="col-md-6">{{f.name}}</div>
              </div>
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