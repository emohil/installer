<#include "/include/common.ftl" />
<style type="text/css">
.grid {
  width: 100%;
  height: 140px;
}
</style>
<#include "/include/common-entry-grid.ftl" />
<script src="static/scripts/city/cityEdit.js"></script>

</head>

<body ng-init="data.id='${id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="CityEditController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">修改城市基本信息</h3>
        </div>
        <input type="hidden" id="provId" name="provId"
          ng-model="data.provId" />
        <div class="panel-body">

          <div class="form-group">
            <label class="control-label required col-md-2">城市编码：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" id="code1"
                name="code1" ng-model="data.code1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.code1.$invalid" disabled />
            </div>
          </div>

          <div class="row">

            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
                <label for="name1"
                  class="control-label required col-md-4">城市名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" id="name1"
                    name="name1" ng-model="data.name1"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.name1.$invalid" required />
                </div>
              </div>
            </div>

            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.addRate.$invalid && (form1.$submitted || form1.addRate.$touched)}">
                <label for="addRate"
                  class="control-label required col-md-4">城市加价率：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" id="addRate"
                    name="addRate" ng-model="data.addRate"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.addRate.$invalid" required>
                </div>
              </div>
            </div>

          </div>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.maxManagers.$invalid && (form1.$submitted || form1.maxManagers.$touched)}">
                <label class="control-label col-md-4">经理人上限：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="maxManagers" ng-model="data.maxManagers">
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.maxWorkers.$invalid && (form1.$submitted || form1.maxWorkers.$touched)}">
                <label class="control-label col-md-4">工人上限：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="maxWorkers" ng-model="data.maxWorkers"
                    required>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.serviceCodes.$invalid && (form1.$submitted || form1.serviceCodes.$touched)}">
            <label class="control-label col-md-2">开通服务：</label>
            <div class="col-md-4">
              <input type="text" class="form-control"
                name="serviceCodes" ng-model="data.serviceCodes"
                required>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.district.$invalid && (form1.$submitted || form1.district.$touched)}">
            <label class="control-label col-md-2">所属片区：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="district"
                ng-model="data.district" required>
            </div>
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-10 col-md-offset-2">
            <button type="submit" class="btn btn-primary"
              ng-click="save();">
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
    </form>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>