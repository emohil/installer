<#include "/include/common.ftl" />
<!-- placeholder -->
<#include "/include/common-entry.ftl" />
<script src="static/scripts/sctype/sort/sctypeSortEdit.js"></script>

</head>

<body ng-init="id='${id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="SctypeSortController">

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
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;编辑服务类别</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">服务类别修改</div>
        <div class="panel-body">
          <div class="form-group">
            <label class="control-label col-md-4">服务类型代码：</label>
            <div class="col-md-4">
              <input type="text" class="form-control"
                ng-model="data.sctype.code1" disabled>
            </div>
            <div class="col-md-4"></div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-4">服务类型名称：</label>
            <div class="col-md-4">
              <input type="text" class="form-control"
                ng-model="data.sctype.name1" disabled>
            </div>
            <div class="col-md-4"></div>
          </div>

          <div class="row divider"></div>

          <div class="form-group">
            <label class="control-label col-md-4">服务类别代码：</label>
            <div class="col-md-4">
              <input type="text" class="form-control"
                ng-model="data.code1" disabled>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-4">服务类别名称：</label>
            <div class="col-md-4">
              <input type="text" class="form-control"
                ng-model="data.name1" disabled>
            </div>
          </div>

          <div class="row">
            <div class="col-md-8 col-md-offset-4">
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();" disabled>
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