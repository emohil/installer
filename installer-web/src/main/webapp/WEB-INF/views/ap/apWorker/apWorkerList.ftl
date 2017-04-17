<#include "/include/common.ftl" />
<title>查看结算批次工人列表</title>
<#include "/include/common-list.ftl" />
<script src="static/scripts/ap/apWorker/apWorkerList.js"></script>
</head>

<body ng-init="apBatchId='${apBatchId}';managerId='${managerId}'">

  <#include "/include/body-begin.ftl" />

  <div ng-controller="ApWorkerController">

    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">付款结算批次信息</div>
      <div class="panel-body">
        <!-- row1 begin -->
        <div class="row">
          <div class="col-md-6">
            <div class="form-group">
              <label class="control-label col-md-4">起止日期：</label>
              <div class="col-md-8">
                <div class="row">
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        ng-model="data.beginDate" disabled /> <span
                        class="input-group-btn">
                        <button type="button"
                          class="btn btn-default disabled">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        ng-model="data.endDate" disabled /> <span
                        class="input-group-btn">
                        <button type="button"
                          class="btn btn-default disabled">
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
            <div class="row">
              <label class="control-label col-md-4">批次描述：</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="data.desc1" disabled>
              </div>
            </div>
          </div>
        </div>
        <!-- row1 end -->

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <label class="control-label col-md-4">结算日期：</label>
              <div class="col-md-4">
                <p class="input-group">
                  <input type="text" class="form-control"
                    ng-model="data.batchDate" disabled /> <span
                    class="input-group-btn">
                    <button type="button"
                      class="btn btn-default disabled">
                      <i class="glyphicon glyphicon-calendar"></i>
                    </button>
                  </span>
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-6 pull-right">
            <button type="button" class="btn btn-success pull-right"
              ng-click="goBack();">
              <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="panel panel-default" ng-if="rows">
      <table class="table table-bordered table-striped">
        <thead>
          <tr>
            <th>经理人姓名</th>
            <th>工人姓名</th>
            <th class="text-center">订单数</th>
            <th class="text-right">工人金额</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td>{{data.managerIdDisp}}</td>
            <td>{{data.workerIdDisp}}</td>
            <td class="text-center"><a
              href="apIndent/apIndentList.do?apBatchId={{data.apBatchId}}&managerId={{data.managerId}}&workerId={{data.workerId}}">{{data.indents}}</a></td>
            <td class="text-right"><a
              href="apIndent/apIndentList.do?apBatchId={{data.apBatchId}}&managerId={{data.managerId}}&workerId={{data.workerId}}">{{data.workerAmt|number:2}}</a></td>
          </tr>
        </tbody>
      </table>
      <div class="panel-footer">
        <!--  -->
        <#include "/include/pagination.ftl" />
      </div>
    </div>
    <#include "/include/body-end.ftl" />
</body>
</html>