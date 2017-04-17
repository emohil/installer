<#include "/include/common.ftl" />
<title>新增结算批次</title>
<#include "/include/common-entry.ftl" />
<script src="static/scripts/ap/apBatch/apBatchAdd.js"></script>
</head>

<body>

  <#include "/include/body-begin.ftl" />

  <div ng-controller="ApBatchController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">付款结算</div>
        <div class="panel-body">
          <!-- row1 begin -->
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
                          name="beginDate" is-open="beginDateOpen"
                          ng-disabled="rows.length != 0"
                          ng-required="true" uib-tooltip="此项为必填项"
                          tooltip-enable="form1.beginDate.$invalid" />
                        <span class="input-group-btn">
                          <button type="button" class="btn btn-default"
                            ng-click="beginDateOpen = !beginDateOpen">
                            <i class="glyphicon glyphicon-calendar"></i>
                          </button>
                        </span>
                      </p>
                    </div>
                    <div class="col-md-6">
                      <p class="input-group"
                        ng-class="{'has-error' : form1.endDate.$invalid && (form1.$submitted || form1.endDate.$touched)}">
                        <input type="text" class="form-control"
                          uib-datepicker-popup ng-model="data.endDate"
                          name="endDate" is-open="endDateOpen"
                          datepicker-options="dateOptions"
                          ng-disabled="rows.length != 0"
                          ng-required="true" uib-tooltip="此项为必填项"
                          tooltip-enable="form1.endDate.$invalid" /> <span
                          class="input-group-btn">
                          <button type="button" class="btn btn-default"
                            ng-click="endDateOpen = !endDateOpen">
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
              <label class="control-label required col-md-4">结算日期：</label>
              <div class="col-md-4">
                <p class="input-group"
                  ng-class="{'has-error' : form1.batchDate.$invalid && (form1.$submitted || form1.batchDate.$touched)}">
                  <input type="text" class="form-control"
                    uib-datepicker-popup ng-model="data.batchDate"
                    name="batchDate" is-open="batchDateOpen"
                    ng-disabled="rows.length != 0"
                    datepicker-options="dateOptions" ng-required="true"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.batchDate.$invalid" /> <span
                    class="input-group-btn">
                    <button type="button" class="btn btn-default"
                      ng-click="batchDateOpen = !batchDateOpen">
                      <i class="glyphicon glyphicon-calendar"></i>
                    </button>
                  </span>
                </p>
              </div>
            </div>
          </div>
          <!-- row1 end -->

          <div class="form-group"
            ng-class="{ 'has-error': form1.desc1.$invalid && (form1.$submitted || form1.desc1.$touched)}">
            <label class="control-label required col-md-2">批次描述：</label>
            <div class="col-md-10">
              <input type="text" class="form-control" name="desc1"
                ng-disabled="rows.length != 0" ng-model="data.desc1"
                uib-tooltip="此项为必填项"
                tooltip-enable="form1.desc1.$invalid" required>
            </div>
          </div>

          <div class="row">
            <div class="col-md-10 col-md-offset-2">
              <button type="submit" class="btn btn-success"
                ng-show="rows.length == 0" ng-click="doPreview();">
                <span class="glyphicon glyphicon-eye-open"></span>&nbsp;预览
              </button>
              <button type="button" class="btn btn-warning"
                ng-show="rows.length != 0" ng-click="doReset();">
                <span class="glyphicon glyphicon-remove"></span>&nbsp;重置
              </button>
              &nbsp;&nbsp;&nbsp;
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();" ng-disabled="rows.length == 0">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
              </button>
              &nbsp;&nbsp;&nbsp;
              <button type="button" class="btn btn-success"
                ng-click="goBack();">
                <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
              </button>
            </div>
          </div>
          <div class="row" ng-hide="true">
            <div class="col-md-6 col-md-offset-2">
              <a class="btn btn-link"><i
                class="glyphicon glyphicon-question-sign"></i>有疑问？没有出现预期的订单？</a>
            </div>
          </div>
        </div>
      </div>

      <div class="panel panel-default form-horizontal"
        ng-hide="rows.length == 0">
        <div class="panel-heading">快速搜索</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6 col-lg-3">
              <div class="row">
                <label class="control-label text-nowrap col-md-4">订单ID：</label>
                <div class="col-md-8">
                  <input type="search" class="form-control"
                    ng-model="search.code1">
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg-3">
              <div class="row">
                <label class="control-label text-nowrap col-md-4">接单工人：</label>
                <div class="col-md-8">
                  <input type="search" class="form-control"
                    ng-model="search.workerName1">
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg-3">
              <div class="row">
                <label class="control-label text-nowrap col-md-4">所属经理人：</label>
                <div class="col-md-8">
                  <input type="search" class="form-control"
                    ng-model="search.managerName1">
                </div>
              </div>
            </div>
          </div>
        </div>
        <table class="table table-bordered table-striped">
          <thead>
            <tr>
              <th style="width: 20px;">&nbsp;</th>
              <th>订单ID</th>
              <th>完工时间</th>
              <th>接单工人</th>
              <th>所属经理人</th>
              <th class="text-right">订单金额</th>
              <th class="text-right">工人金额</th>
              <th class="text-right">经理人金额</th>
            </tr>
          </thead>
          <tbody ng-repeat="data in filterRows">
            <tr>
              <td><input type="checkbox" ng-model="data.checked"></td>
              <td>{{data.code1}}</td>
              <td>{{data.finishDate}}</td>
              <td>{{data.workerName1}}</td>
              <td>{{data.managerName1}}</td>
              <td class="text-right">{{data.sum|number:2}}</td>
              <td class="text-right">{{data.workerFee|number:2}}</td>
              <td class="text-right">{{data.managerFee|number:2}}</td>
            </tr>
          </tbody>
        </table>
        <div class="panel-footer">
          <!--  -->
          <#include "/include/pagination.ftl" />
        </div>
      </div>
    </form>
    <#include "/include/body-end.ftl" />
</body>
</html>