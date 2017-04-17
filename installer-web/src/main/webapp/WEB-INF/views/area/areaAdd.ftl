<#include "/include/common.ftl" />
<style type="text/css">
.grid {
  width: 100%;
  height: 180px;
}
</style>
<#include "/include/common-entry-grid.ftl" />
<script src="static/scripts/area/areaAdd.js"></script>

</head>

<body ng-init="provId='${provId}'">

  <#include "/include/body-begin.ftl" />

  <div ng-controller="CityAddController"
    ng-init="data.cityId='${data.id}'">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">城市基本信息</h3>
        </div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label for="name1" class="control-label col-md-4">城市名称：</label>
                <div class="col-md-7">
                  <input type="text" disabled class="form-control"
                    id="name1" value="${data.name1}" />
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label for="addRate" class="control-label col-md-4">城市加价率：</label>
                <div class="col-md-7">
                  <input type="text" disabled class="form-control"
                    value="${data.addRate}">
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">经理人上限：</label>
                <div class="col-md-7">
                  <input type="text" disabled class="form-control"
                    value="${data.maxManagers}">
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">工人上限：</label>
                <div class="col-md-7">
                  <input type="text" disabled class="form-control"
                    value="${data.maxWorkers}">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">开通片区</h3>
        </div>
        <div class="panel-body">
          <div class="form-group" ng-controller="AreaController">

            <label class="control-label col-md-2">开通区域：
              <div
                class="glyphicon glyphicon-plus btn btn-warning btn-xs"
                uib-tooltip="添加" ng-click="addRow()"></div>
            </label>
            <div class="col-md-10">
              <div ui-grid="gridOptions" ui-grid-edit ui-grid-cellnav
                ui-grid-validate class="grid"></div>
            </div>
          </div>
        <div class="row">
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
      </div>
    </form>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>