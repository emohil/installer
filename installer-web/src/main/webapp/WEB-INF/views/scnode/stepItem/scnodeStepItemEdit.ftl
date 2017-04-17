<#include "/include/common.ftl" />
<!-- placeholder -->
<#include "/include/common-entry.ftl" />
<script src="static/scripts/scnode/stepItem/scnodeStepItemEdit.js"></script>

</head>

<body ng-init="id='${id}'">
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
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;编辑服务步驟子项</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">编辑服务步驟子项</div>
        <div class="panel-body">
          <div class="form-group">
            <label for="name1" class="control-label col-md-2">服务步骤名称：</label>
            <div class="col-md-10">
              <input type="text" class="form-control"
                ng-model="data.scnodeStep.name1" disabled />
            </div>
          </div>
          <div class="form-group">
            <label for="addRate" class="control-label col-md-2">服务步骤描述：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control"
                ng-model="data.scnodeStep.desc1" disabled></textarea>
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
            <div class="col-md-6"
              ng-if="data.scnodeStep.stepType == 'CONFIRM'">
              <div class="form-group"
                ng-class="{'has-error' : form1.itemType.$invalid && (form1.$submitted || form1.itemType.$touched)}">
                <label class="control-label required col-md-4">步骤确认类型：</label>
                <div class="col-md-8">
                  <select type="text" class="form-control"
                    name="itemType" ng-model="data.itemType" disabled>
                    <#list confirmTypeList as s>
                    <option value="${s.value}">${s.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>

            <div class="col-md-6"
              ng-if="data.scnodeStep.stepType == 'UPLOAD'">
              <div class="form-group"
                ng-class="{'has-error' : form1.itemType.$invalid && (form1.$submitted || form1.itemType.$touched)}">
                <label class="control-label required col-md-4">最少图片限制：</label>
                <div class="col-md-8">
                  <input type="text" positive-integer
                    class="form-control" name="minPhoto"
                    ng-model="data.minPhoto" required>
                </div>
              </div>
            </div>
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
              <textarea type="text" class="form-control" name="desc1"
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
                file-model="file.sketchFiles" accept="image/*" multiple ng-disabled="!data.isSketch"/>
            </div>
          </div>
          <div class="row" ng-if="data.isSketch">
            <div class="col-md-10 col-md-offset-2">
              <div class="row" ng-repeat="f in sketchFiles">
                <div class="col-md-4">{{f.fileName}}</div>
                <div class="col-md-4">
                  <div class="btn-group btn-xs">
                    <a class="btn btn-link" href="{{f.fileUrl}}">【预览】</a>
                    <a class="btn btn-link"
                      href="fs/download.do?fileid={{f.id}}">【下载】</a><a
                      class="btn btn-link" ng-click="removeFile(f.id);">【删除】</a>
                  </div>
                </div>
              </div>
              <div class="row" ng-repeat="f in file.sketchFiles">
                <div class="col-md-4">{{f.name}}</div>
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
              <button type="button" class="btn btn-danger"
                ng-click="doDelete();">
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