<#include "/include/common.ftl" /> <#include "/include/common-list.ftl"
/>
<script src="static/scripts/city/cityList.js"></script>
</head>
<body ng-controller="CityListController" ng-init="sf.provId=${provId}">
  <#include "/include/body-begin.ftl" />

  <div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">城市列表</h3>
      </div>
      <div class="panel-body">

        <form class="sections" method="post">
          <div class="section-nopadding">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>城市编码</th>
                  <th>名称</th>
                  <th>加价率</th>
                  <th>经理人上限</th>
                  <th>工人上限</th>
                  <th>所属片区</th>
                  <th>开通服务</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody ng-repeat="data in rows">
                <tr>
                  <td>{{data.code1}}</td>
                  <td class="tableA"><#if data.status=='NORMAL'><a
                    href="city/cityEdit.do?id={{data.id}}">{{data.name1}}</a><#else>{{data.name1}}</#if>
                  </td>
                  <td>{{data.addRate}}</td>
                  <td>{{data.maxManagers}}</td>
                  <td>{{data.maxWorkers}}</td>
                  <td>{{data.district}}</td>
                  <td>{{data.serviceCodes}}</td>
                  <td><span ng-if="data.status == 'ENABLED'"><a
                      class="btn btn-primary btn-xs"
                      href="area/areaAdd.do?cityId={{data.id}}&provId=${provId}">区域管理</a>&nbsp;<a
                      class="btn btn-primary btn-xs"
                      href="city/closeCity.do?id={{data.id}}&provId=${provId}">停用</a></span>
                    <span ng-if="data.status == 'DISABLED'"><a
                      class="btn btn-primary btn-xs"
                      href="city/cityEdit.do?id={{data.id}}">启用</a></span></td>
                </tr>
              </tbody>
            </table>
          </div>
        </form>
        <div class="pull-right">
          <button type="button" class="btn btn-success"
                ng-click="goBack();">
                <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
              </button>
        </div>
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>