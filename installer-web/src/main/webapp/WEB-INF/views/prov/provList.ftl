<#include "/include/common.ftl" />

<#include "/include/common-list.ftl" />
<script src="static/scripts/prov/provList.js"></script>
</head>
<body ng-controller="ProvListController">
  <#include "/include/body-begin.ftl" />

  <div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <h3 class="panel-title">省份列表</h3>
      </div>
      <div class="panel-body">
        <form class="sections" method="post">
          <div class="section-nopadding">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>省份编码</th>
                  <th>名称</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody ng-repeat="data in rows">
                <tr>
                  <td>{{data.code1}}</td>
                  <td>{{data.name1}}</td>
                  <td>
                  <span ng-if="data.status == 'ENABLED'"><a
                    class="btn btn-primary btn-xs"
                    href="city/cityList.do?sf.provId={{data.id}}">城市管理</a>&nbsp;<a
                    class="btn btn-primary btn-xs"
                    href="prov/closeProv.do?id={{data.id}}">停用</a></span>
                    <span ng-if="data.status == 'DISABLED'"><a class="btn btn-primary btn-xs"
                    href="prov/openProv.do?id={{data.id}}">启用</a></span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </form>
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>