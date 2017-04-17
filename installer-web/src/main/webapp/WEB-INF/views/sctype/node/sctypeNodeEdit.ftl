<#include "/include/common.ftl" />
<!-- placeholder -->
<#include "/include/common-entry-debug.ftl" />
<script src="static/scripts/sctype/node/sctypeNodeEdit.js"></script>

</head>

<body ng-init="sctypeId='${sctypeId}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="SctypeNodeController">

    <div class="row">
      <div class="col-md-12">
        <ol class="breadcrumb">
          <li><a href="homepage.do"><span
              class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
          <li><a><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;系统设置</a></li>
          <li><a href="sctype/sctypeTree.do"><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;服务类型</a></li>
          <li class="active"><span
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;编辑服务类型的节点</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">服务类型节点修改</div>
        <div class="panel-body">
          <div class="form-group">
            <label class="control-label col-md-2">服务类型编码：</label>
            <div class="col-md-4">
              <input type="text" class="form-control"
                ng-model="data.code1" disabled>
            </div>
            <div class="col-md-4"></div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-2">服务类型名称：</label>
            <div class="col-md-4">
              <input type="text" class="form-control"
                ng-model="data.name1" disabled>
            </div>
            <div class="col-md-4"></div>
          </div>

          <div class="form-group">
            <label class="control-label col-md-2">服务节点：</label>
            <div class="col-md-10">
              <div class="row">
                <#list scnodeList as scnode>
                <div class="col-md-2">
                  <label class="checkbox-inline"><input
                    type="checkbox" ng-model="data.nodes.ID${scnode.id}">${scnode.name1}</label>
                </div>
                </#list>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-md-8 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();">
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
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>