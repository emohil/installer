<#include "/include/common.ftl" />
<title>服务节点</title>
<#include "/include/common-entry.ftl"/>

<link href="static/treegrid/treeGrid.css" rel="styleSheet" />

<script src="static/treegrid/tree-grid-directive.js"
  type="text/javascript"></script>

<script src="static/scripts/indent/indentNodeTree.js"></script>

</head>

<body ng-init="data.id='${id}'">

  <#include "/include/body-begin.ftl" />

  <div ng-controller="ScnodeController">
  
  <div class="row">
      <div class="col-md-12">
        <ol class="breadcrumb">
          <li><a href="homepage.do"><span
              class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
          <li><a><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;订单管理</a></li>
          <li><a href="indent/indentList.do"><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;全部订单</a></li>
          <li class="active"><a><span
              class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;订单节点</a></li>
        </ol>
      </div>
    </div>
  
    <div class="panel panel-default">
      <div class="panel-heading">订单节点</div>
      <div class="panel-body" ng-controller="TreeGridController">

        <div class="form-group">
          <div class="btn-group" role="group">
            <button type="button" class="btn btn-success"
              ng-click="tree.expand_all()">
              <span class="glyphicon glyphicon-plus"></span>&nbsp;展开
            </button>
            <button type="button" class="btn btn-warning"
              ng-click="tree.collapse_all()">
              <span class="glyphicon glyphicon-minus"></span>&nbsp;收起
            </button>
          </div>
        </div>
        <div class="form-group">
          <tree-grid tree-data="tree_data" col-defs="col_defs"
            tree-control="tree" expand-on="expanding_property"
            expand-level="1" icon-leaf="glyphicon glyphicon-cog"
            icon-expand="glyphicon glyphicon-plus"
            icon-collapse="glyphicon glyphicon-minus"></tree-grid>
        </div>
        <div class="row">
          <div class="col-md-11">
            <button type="button" class="btn btn-warning pull-right"
              ng-click="lookException();">
              <span class="glyphicon glyphicon-eye-open"></span>&nbsp;查看异常列表
            </button>
          </div>
          <div class="col-md-1">
            <button type="button" class="btn btn-success pull-right"
              ng-click="goBack();">
              <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>