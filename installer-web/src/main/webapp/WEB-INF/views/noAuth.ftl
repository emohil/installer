<#include "/include/common.ftl" />
<link rel="stylesheet" href="static/css/homepage.css" type="text/css" />
<script src="static/angular/angular.js"></script>
<script type="text/javascript">
angular.module('app', []);
</script>
</head>
<body>
  <#include "/include/body-begin.ftl" />

  <div class="row">
    <div class="col-md-12">
      <div class="alert alert-warning">对不起，您没有权限查看此页面！</div>
    </div>
  </div>

  <#include "/include/body-end.ftl" />
</body>
</html>