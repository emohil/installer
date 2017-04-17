<#include "/include/common.ftl" />
<title>付款结算列表</title>
<#include "/include/common-list.ftl" />
<script src="static/scripts/ap/apBatch/apBatchList.js"></script>
</head>
<body>
  <#include "/include/body-begin.ftl" />

  <div ng-controller="ApBatchController">
    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">结算查询</div>
      <div class="panel-body">
        <!-- row1 begin -->
        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <label class="control-label text-nowrap col-md-4 col-lg-2">结算日期</label>
              <div class="col-md-8 col-lg-10">
                <div class="row">
                  <div class="col-md-6">
                    <div class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup
                        ng-model="sf.batchDateBegin"
                        is-open="batchDateBeginOpen" />
                      <!-- calendar btn -->
                      <span class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="batchDateBeginOpen = !batchDateBeginOpen">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <div class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.batchDateEnd"
                        is-open="batchDateEndOpen" />
                      <!-- calendar btn -->
                      <span class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="batchDateEndOpen = !batchDateEndOpen">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4 col-lg-2">批次描述</label>
              <div class="col-md-8 col-lg-10">
                <input type="text" class="form-control"
                  ng-model="sf.desc1">
              </div>
            </div>
          </div>
        </div>
        <!-- row1 end -->
        <div class="row">
          <div class="col-md-6 col-lg-3 pull-right">
            <div class="btn-group pull-right">
              <button class="btn btn-warning" ng-click="query()">
                <span class="glyphicon glyphicon-search"></span>&nbsp;筛选
              </button>
              <a class="btn btn-primary" href="apBatch/apBatchAdd.do"><span
                class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
            </div>
          </div>
        </div>
      </div>
      <!-- panel body end -->
    </div>
    <!-- panel end -->

    <div class="panel panel-default">
      <table class="table table-bordered table-striped">
        <thead>
          <tr>
            <th>批次描述</th>
            <th width="100px">结算日期</th>
            <th width="80px">批次状态</th>
            <th width="80px" class="text-center">总订单数</th>
            <th width="100px" class="text-center">经理人数</th>
            <th width="100px" class="text-center">工人数</th>
            <th width="80px" class="text-right">订单金额</th>
            <th width="80px" class="text-right">工人金额</th>
            <th width="90px" class="text-right">经理人金额</th>
            <th width="80px" class="text-right">利润</th>
            <th width="130px">操作</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td>{{data.desc1|limitTo:25}}</td>
            <td>{{data.batchDate}}</td>
            <td>{{data.batchStatusDisp}}</td>
            <td class="text-center"><a
              href="apIndent/apIndentList.do?apBatchId={{data.id}}">{{data.indents}}</a></td>
            <td class="text-center"><a
              href="apManager/apManagerList.do?apBatchId={{data.id}}">{{data.managers}}</a></td>
            <td class="text-center"><a
              href="apWorker/apWorkerList.do?apBatchId={{data.id}}">{{data.workers}}</a></td>
            <td class="text-right"><a
              href="apIndent/apIndentList.do?apBatchId={{data.id}}">{{data.indentAmt|number:2}}</a></td>
            <td class="text-right"><a
              href="apWorker/apWorkerList.do?apBatchId={{data.id}}">{{data.workerAmt|number:2}}</a></td>
            <td class="text-right"><a
              href="apManager/apManagerList.do?apBatchId={{data.id}}">{{data.managers}}{{data.managerAmt|number:2}}</a></td>
            <td class="text-right"><a
              href="apIndent/apIndentList.do?apBatchId={{data.id}}">{{data.profitAmt|number:2}}</a></td>
            <td><span ng-if="data.batchStatus == 'UNTREATED'">
                <a class="btn btn-danger btn-xs"
                ng-click="doDelete(data.id);"><span
                  class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>&nbsp;
                <a ng-click="doPost(data.id);"
                class="btn btn-warning btn-xs"><span
                  class="glyphicon glyphicon-check"></span>&nbsp;过账</a>
            </span></td>
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