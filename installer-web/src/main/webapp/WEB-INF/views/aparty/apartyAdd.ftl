<#include "/include/common.ftl" />
<title>新增甲方</title>
<style type="text/css">
.grid {
  width: 100%;
  height: 140px;
}
</style>
<#include "/include/common-entry-grid.ftl" /> <#include
"/include/common-cityselect.ftl" />

<script src="static/scripts/aparty/apartyAdd.js"></script>

</head>

<body>

  <#include "/include/body-begin.ftl" />

  <div ng-controller="ApartyAddController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">甲方基本信息</div>
        <div class="panel-body">

          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{ 'has-error': form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
                <label for="name1"
                  class="control-label required col-md-4">甲方名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" id="name1"
                    name="name1" ng-model="data.name1"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.name1.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{ 'has-error': form1.industry.$invalid && (form1.$submitted || form1.industry.$touched)}">
                <label class="control-label required col-md-4">甲方行业：</label>
                <div class="col-md-8">
                  <select class="form-control" ng-model="data.industry"
                    name="industry" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.industry.$invalid" required>
                    <#list industryList as industry>
                    <option value="${industry.value}">${industry.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
          </div>

          <div class="form-group"
            ng-class="{ 'has-error': form1.regionProv.$invalid && (form1.$submitted || form1.regionProv.$touched)}"
            cityselect>
            <label class="control-label required col-md-2">甲方地址：</label>
            <div class="col-md-2">
              <select class="form-control" ng-model="data.regionProv"
                name="regionProv"
                ng-options="prov.v as prov.n for prov in provs" required>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!cities">
              <select class="form-control" name="regionCity"
                ng-model="data.regionCity"
                ng-options="city.v as city.n for city in cities">
              </select>
            </div>
            <div class="col-md-2" ng-hide="!dists">
              <select class="form-control" name="regionDist"
                ng-model="data.regionDist"
                ng-options="dist.v as dist.n for dist in dists">
              </select>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.addr1.$invalid && (form1.$submitted || form1.addr1.$touched)}">
            <label class="control-label required col-md-2">通信地址：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control" name="addr1"
                ng-model="data.addr1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.addr1.$invalid" required></textarea>
            </div>
          </div>
          <div class="form-group"
            ng-class="{ 'has-error': form1.remarks.$invalid && (form1.$submitted || form1.remarks.$touched)}">
            <label class="control-label required col-md-2">甲方备注：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control" name="remarks"
                ng-model="data.remarks" uib-tooltip="此项为必填项"
                tooltip-enable="form1.remarks.$invalid" required></textarea>
            </div>
          </div>
        </div>
      </div>

      <div class="panel panel-default">
        <div class="panel-heading">甲方／乙方联系信息</div>
        <div class="panel-body">
          <div class="form-group" ng-controller="Contact1Controller">
            <label class="control-label required col-md-2">甲方联系人：
              <div
                class="glyphicon glyphicon-plus btn btn-warning btn-xs"
                uib-tooltip="添加" ng-click="addRow()"></div>
            </label>
            <div class="col-md-10">
              <div ui-grid="gridOptions" ui-grid-edit ui-grid-cellnav
                ui-grid-validate class="grid"></div>
            </div>
          </div>

          <div class="form-group" ng-controller="Contact2Controller">
            <label class="control-label required col-md-2">乙方联系人：
              <div
                class="glyphicon glyphicon-plus btn btn-warning btn-xs"
                uib-tooltip="添加" ng-click="addRow()"></div>
            </label>
            <div class="col-md-10">
              <div ui-grid="gridOptions" ui-grid-edit ui-grid-cellnav
                class="grid"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">甲方财务信息</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error': form1.invoiceTop.$invalid && (form1.$submitted || form1.invoiceTop.$touched)}">
                <label class="control-label required col-md-4">发票抬头：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="invoiceTop" ng-model="data.finance.invoiceTop"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.invoiceTop.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error': form1.revolveTime.$invalid && (form1.$submitted || form1.revolveTime.$touched)}">
                <label class="control-label required col-md-4">甲方账期：</label>
                <div class="col-md-8">
                  <#list revolveTimeList as revolveTime> <input
                    type="radio" ng-model="data.finance.revolveTime"
                    value="${revolveTime.value}" required>&nbsp;&nbsp;${revolveTime.text}&nbsp;&nbsp;&nbsp;&nbsp;
                  </#list>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error': form1.openBank.$invalid && (form1.$submitted || form1.openBank.$touched)}">
                <label class="control-label required col-md-4">开户行：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="openBank" ng-model="data.finance.openBank"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.openBank.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error': form1.cardNo.$invalid && (form1.$submitted || form1.cardNo.$touched)}">
                <label class="control-label required col-md-4">账号：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="cardNo"
                    ng-model="data.finance.cardNo" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.cardNo.$invalid" required>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-10 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                fng-click="save();">
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
    <#include "/include/body-end.ftl" />
</body>
</html>