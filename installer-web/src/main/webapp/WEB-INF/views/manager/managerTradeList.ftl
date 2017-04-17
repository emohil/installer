<#include "/include/common.ftl" />
<title>经理人提现管理查询</title>
<#include "/include/common-list.ftl" />
<script src="static/scripts/account/managerTradeList.js"></script>
</head>
<body>
  <#include "/include/body-begin.ftl" />
  <div ng-controller="ManagerTradeListController">

    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">经理人提现管理查询</div>
      <div class="panel-body">
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">经理人工号</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.accountCode1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">经理人姓名</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.accountName1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">提现状态</label>
              <div class="col-md-8">
                <select class="form-control" ng-model="sf.tradeStatus">
                  <#list tradeStatusList as item>
                  <option value="${item.value}">${item.text}</option>
                  </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3 pull-right">
            <button class="btn btn-warning pull-right"
              ng-click="query()">
              <span class="glyphicon glyphicon-search"></span>&nbsp;筛选
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="panel panel-default">
      <table class="table table-bordered table-striped">
        <thead>
          <tr>
            <th>经理人姓名</th>
            <th>银行卡号</th>
            <th>提现金额</th>
            <th>申请时间</th>
            <th>提现状态</th>
            <th>执行人</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td>{{data.managerName}}</td>
            <td>{{data.cardNum}}</td>
            <td>{{data.curAmt}}</td>
            <td>{{data.tradeDate}}</td>
            <td>{{data.tradeStatusDisp}}</td>
            <td>{{data.modifyBy}}</td>
            <td><span ng-if="data.tradeStatus == 'TREATMENT'">
                <a href="managerTrade/managerTradeEdit.do?id={{data.id}}&&status=0"
                class="btn btn-success btn-xs">查看详情</a>
            </span>
            <span ng-if="data.tradeStatus != 'TREATMENT'">
                <a href="managerTrade/managerTradeEdit.do?id={{data.id}}&&status=1"
                class="btn btn-success btn-xs">查看结果</a>
            </span>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="panel-footer">
        <!--  -->
        <#include "/include/pagination.ftl" />
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>