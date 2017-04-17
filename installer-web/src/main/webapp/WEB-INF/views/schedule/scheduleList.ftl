<#include "/include/common.ftl" />
<title>定时计划列表</title>
<#include "/include/common-list-debug.ftl" />
<script src="static/scripts/schedule/scheduleList.js"></script>
</head>
<body>
  <#include "/include/body-begin.ftl" />

  <div ng-controller="ScheduleController">
    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">计划查询</div>
      <div class="panel-body">
        <!-- row1 begin -->
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4 col-lg-2">名称</label>
              <div class="col-md-8 col-lg-10">
                <input type="text" class="form-control"
                  ng-model="sf.name1">
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4 col-lg-2">类名</label>
              <div class="col-md-8 col-lg-10">
                <input type="text" class="form-control"
                  ng-model="sf.qualifiedName">
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
      <!-- panel body end -->
    </div>
    <!-- panel end -->

    <div class="panel panel-default">
      <table class="table table-bordered table-striped">
        <thead>
          <tr>
            <th width="200px">名称</th>
            <th>全限定类</th>
            <th width="200px">执行表达式</th>
            <th width="80px">状态</th>
            <th width="130px">操作</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td>{{data.name1}}</td>
            <td>{{data.qualifiedName}}</td>
            <td>{{data.cronExpression}}</td>
            <td>{{data.statusDisp}}</td>
            <td><span ng-if="data.status == 'ENABLED'"> <a
                class="btn btn-warning btn-xs"
                ng-click="doDisable(data.id);"><span
                  class="glyphicon glyphicon-pause"></span>&nbsp;停用</a>&nbsp;
                <a class="btn btn-primary btn-xs"
                ng-click="doTrigger(data.id);"><span
                  class="glyphicon glyphicon-play-circle"></span>&nbsp;运行</a>
            </span> <span ng-if="data.status == 'DISABLED'"> <a
                class="btn btn-success btn-xs"
                ng-click="doEnable(data.id);"><span
                  class="glyphicon glyphicon-play"></span>&nbsp;启用</a>
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