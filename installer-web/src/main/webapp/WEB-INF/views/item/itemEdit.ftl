<#include "/include/common.ftl" />
<title>修改项目信息</title>
<style type="text/css">
.grid {
  width: 100%;
  height: 140px;
}
</style>
<#include "/include/common-entry-grid-debug.ftl" />
<!--  -->
<#include "/include/common-cityselect2.ftl" />
<script type="text/javascript" src="static/js/jquery-1.11.1.min.js"></script>
<script src="static/scripts/item/itemEdit.js"></script>

</head>

<body ng-init="data.id='${id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="ItemController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">项目基本信息</div>
        <div class="panel-body">

          <div class="form-group"
            ng-class="{ 'has-error': form1.apartyName1.$invalid && (form1.$submitted || form1.apartyName1.$touched)}">
            <label for="name1" class="control-label required col-md-2">甲方名称：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="apartyName1"
                ng-model="data.aparty.name1" disabled />
            </div>
          </div>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
                <label for="name1"
                  class="control-label required col-md-4">项目名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="name1"
                    ng-model="data.name1" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.name1.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.itemMoney.$invalid && (form1.$submitted || form1.itemMoney.$touched)}">
                <label for="itemMoney"
                  class="control-label required col-md-4">项目金额：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="itemMoney" ng-model="data.itemMoney"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.itemMoney.$invalid" numeric
                    required>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">起止日期：</label>
                <div class="col-md-8">
                  <div class="row">
                    <div class="col-md-6">
                      <p class="input-group"
                        ng-class="{'has-error' : form1.beginDate.$invalid && (form1.$submitted || form1.beginDate.$touched)}">
                        <input type="text" class="form-control"
                          uib-datepicker-popup ng-model="data.beginDate"
                          name="beginDate" is-open="beginDate.opened"
                          ng-required="true" uib-tooltip="此项为必填项"
                          tooltip-enable="form1.beginDate.$invalid" />
                        <span class="input-group-btn">
                          <button type="button" class="btn btn-default"
                            ng-click="openBeginDate()">
                            <i class="glyphicon glyphicon-calendar"></i>
                          </button>
                        </span>
                      </p>
                    </div>
                    <div class="col-md-6">
                      <p class="input-group"
                        ng-class="{'has-error' : form1.overDate.$invalid && (form1.$submitted || form1.overDate.$touched)}">
                        <input type="text" class="form-control"
                          uib-datepicker-popup ng-model="data.overDate"
                          name="overDate" is-open="overDate.opened"
                          datepicker-options="dateOptions"
                          ng-required="true" uib-tooltip="此项为必填项"
                          tooltip-enable="form1.overDate.$invalid" /> <span
                          class="input-group-btn">
                          <button type="button" class="btn btn-default"
                            ng-click="openOverDate()">
                            <i class="glyphicon glyphicon-calendar"></i>
                          </button>
                        </span>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.indentAmt.$invalid && (form1.$submitted || form1.indentAmt.$touched)}">
                <label for="indentAmt"
                  class="control-label required col-md-4">预计单量：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="indentAmt" ng-model="data.indentAmt"
                    uib-tooltip="此项为必填项" positive-integer
                    tooltip-enable="form1.indentAmt.$invalid" required>
                </div>
              </div>
            </div>
          </div>

          <div class="form-group"
            ng-class="{'has-error' : form1.regionProv.$invalid && (form1.$submitted || form1.regionProv.$touched)}"
            cityselect2>
            <label class="control-label required col-md-2">服务地址：</label>
            <div class="col-md-2">
              <select class="form-control" name="regionProv"
                ng-model="data.areaProvince"
                ng-options="prov.v as prov.n for prov in provs" required>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!cities">
              <select class="form-control" name="regionCity"
                ng-model="data.areaCity"
                ng-options="city.v as city.n for city in cities"
                required>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!dists">
              <select class="form-control" name="regionDist"
                ng-model="data.areaDist"
                ng-options="dist.v as dist.n for dist in dists">
              </select>
            </div>
          </div>


          <div class="form-group">
            <label for="" class="control-label required col-md-2">服务类型：</label>
            <div class="col-md-9">
              <input type="hidden" id="serveSortIds"
                value="${serveSortIds}" />
              <table class="table table-bordered">
                <#list serveTypeList as serveType>
                <tr>
                  <td>${serveType.name1}</td>
                  <td><#list serveType.sortList as serveSort><input
                    type="checkbox" value="${serveSort.id}">
                    ${serveSort.name1}&nbsp;&nbsp;&nbsp;&nbsp; </#list>
                  </td>
                </tr>
                </#list>
              </table>
              <button type="button" class="btn btn-primary"
                ng-click="add();">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;确定
              </button>
            </div>
          </div>

          <div class="form-group"
            ng-class="{ 'has-error': form1.pactNum.$invalid && (form1.$submitted || form1.pactNum.$touched)}">
            <label for="pactNum" class="control-label required col-md-2">合同号：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="pactNum"
                ng-model="data.pactNum" uib-tooltip="此项为必填项"
                tooltip-enable="form1.pactNum.$invalid" required>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label required col-md-2">上传合同：</label>

            <div class="col-md-10">
              <div class="row" ng-repeat="f in contractFiles">
                <div class="col-md-4">
                  <span>{{f.fileName}}</span>
                </div>
                <div class="col-md-4">
                  <a href="fs/download.do?fileid={{f.id}}">【下载】</a><a
                    ng-click="delPact(f.id);">【删除】</a>
                </div>
              </div>
              <div class="row">
                <div class="col-md-12">
                  <input type="file" name="imgFiles"
                    file-model="file.imgFiles" multiple />
                </div>
              </div>
              <div class="row" ng-repeat="f in file.imgFiles">
                <div class="col-md-12">
                  <span>{{f.name}} 
                </div>
              </div>
            </div>

          </div>

          <div class="form-group"
            ng-class="{ 'has-error': form1.indentRequire.$invalid && (form1.$submitted || form1.indentRequire.$touched)}">
            <label for="indentRequire"
              class="control-label required col-md-2">项目要求：</label>
            <div class="col-md-10">
              <textarea class="form-control" name="indentRequire"
                ng-model="data.indentRequire" uib-tooltip="此项为必填项"
                tooltip-enable="form1.indentRequire.$invalid" required></textarea>
            </div>
          </div>
        </div>
      </div>

      <div class="panel panel-default">
        <div class="panel-heading">项目报价信息</div>
        <div class="panel-body">
          <table class="table table-bordered" id="offer">
            <thead>
              <tr>
                <th>类型</th>
                <th>类别</th>
                <th>内容</th>
                <th>单位</th>
                <th>报价</th>
                <th>工人收入%</th>
                <th>经理人收入%</th>
                <th>利润率%</th>
              </tr>
            </thead>
            <tbody>
            </tbody>
          </table>
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
          <div class="col-md-10 col-md-offset-2">
            <button type="submit" class="btn btn-primary"
              ng-click="save();">
              <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
            </button>
            &nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-danger"
              ng-click="doDelete();">
              <span class="glyphicon glyphicon-trash"></span>&nbsp;删除
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
<script type="text/javascript">
  function jian(index) {
    if (isNaN($("#workerRate" + index).val()) || isNaN($("#managerRate" + index).val())) {
      $("#profitRate" + index).val("");
    } else {
      $("#profitRate" + index).val(
          eval(100 - $("#workerRate" + index).val() - $("#managerRate" + index).val()))
    }
  }
</script>
</html>