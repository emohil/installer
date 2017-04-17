<#include "/include/common.ftl" />
<title>服务节点</title>
<#include "/include/common-entry.ftl"/>

<link href="static/treegrid/treeGrid.css" rel="styleSheet" />

<script src="static/treegrid/tree-grid-directive.js"
  type="text/javascript"></script>

<script src="static/scripts/scnode/scnode/scnodeTree.js"></script>

</head>

<body>
  <#include "/include/body-begin.ftl" />

  <div ng-controller="ScnodeController">
    <div class="row">
      <div class="col-md-12">
        <ol class="breadcrumb">
          <li><a href="homepage.do"><span
              class="glyphicon glyphicon-home"></span>&nbsp;Home</a></li>
          <li><a><span
              class="glyphicon glyphicon-triangle-right"></span>&nbsp;系统设置</a></li>
          <li class="active"><span
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;服务节点展示树</li>
        </ol>
      </div>
    </div>

    <div class="panel panel-default">
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
          <button type="button" class="btn btn-primary pull-right"
            ng-click="addScnode();">
            <span class="glyphicon glyphicon-plus"></span>&nbsp;新增服务类型
          </button>
        </div>
        <div class="form-group">
          <tree-grid tree-data="tree_data" col-defs="col_defs"
            tree-control="tree" expand-on="expanding_property"
            expand-level="2" icon-leaf="glyphicon glyphicon-cog"
            icon-expand="glyphicon glyphicon-plus"
            icon-collapse="glyphicon glyphicon-minus"></tree-grid>
        </div>
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>