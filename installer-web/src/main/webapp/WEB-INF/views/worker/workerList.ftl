<#include "/include/common.ftl" />
<title>工匠管理查询</title>
<#include "/include/common-list.ftl" />
<script src="static/scripts/account/workerList.js"></script>
</head>
<body>
  <#include "/include/body-begin.ftl" />
  <div ng-controller="WorkerListController">

    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">工匠管理查询</div>
      <div class="panel-body">
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">工匠工号</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.code1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">工匠姓名</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.account.name1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">工匠电话</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.account.mobile">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">审核状态</label>
              <div class="col-md-8">
                <select class="form-control" ng-model="sf.status">
                  <#list checkStatusList as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.status}'>selected="true"</#if>
                    >${item.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">角色状态</label>
              <div class="col-md-8">
                <select class="form-control" ng-model="sf.roleStatus">
                  <#list roleStatusList as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.roleStatus}'>selected="true"</#if>
                    >${item.text}</option> </#list>
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
            <th>工匠工号</th>
            <th>工匠姓名</th>
            <th>审核状态</th>
            <th>角色状态</th>
            <th>审核人</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td>{{data.code1}}</td>
            <td>{{data.account.name1}}</td>
            <td>{{data.statusDisp}}</td>
            <td>{{data.roleStatusDisp}}</td>
            <td>{{data.verifier}}</td>
            <td><span ng-if="data.status == 'UNCHECK'"> <a
                href="worker/workerEdit.do?id={{data.id}}&&status=0"
                class="btn btn-success btn-xs">审验资质</a>
            </span> <span ng-if="data.status != 'UNCHECK'"> <a
                href="worker/workerEdit.do?id={{data.id}}&&status=1"
                class="btn btn-success btn-xs">查看</a>
            </span> <span
              ng-if="data.status == 'PASS' && data.roleStatus == 'ENABLED'">
                <a href="worker/controlWorker.do?id={{data.id}}&&status=0"
                class="btn btn-success btn-xs">停用角色</a>
            </span> <span
              ng-if="data.status == 'PASS' && data.roleStatus == 'DISABLED'">
                <a href="worker/controlWorker.do?id={{data.id}}&&status=1"
                class="btn btn-success btn-xs">启用角色</a>
            </span><span
              ng-if="data.vehicleStatus == 'UNCHECK' && data.holdWay != 'NONE'">
                <a href="worker/vehicleEdit.do?id={{data.id}}&&status=0"
                class="btn btn-success btn-xs">审验车辆</a>
            </span><span
              ng-if="data.vehicleStatus != 'UNCHECK' && data.holdWay != 'NONE'">
                <a href="worker/vehicleEdit.do?id={{data.id}}&&status=1"
                class="btn btn-success btn-xs">查看车辆</a>
            </span> <a href="worker/mgrList.do?id={{data.id}}"
              class="btn btn-success btn-xs">经理人列表</a></td>
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