<#include "/include/common.ftl" />
<title>Form</title>
<#include "/include/common-entry.ftl" />

<script src="static/scripts/demo/form.js"></script>

</head>

<body>
  <#include "/include/body-begin.ftl" />


  <div ng-controller="FormController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-body">
          <div class="form-group">
            <div class="col-md-2 col-md-offset-2">
              <input type="file" file-model="data.file1">
            </div>
          </div>
         
          <div class="form-group">
            <div class="col-md-2 col-md-offset-2">
              <input type="text" ng-model="data.name1">
            </div>
          </div>

          <div class="row">
            <div class="col-md-2 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                ng-click="save()">保存1</button>
              <button type="button" class="btn btn-primary"
                ng-click="save()">保存2</button>
            </div>
          </div>

          <div class="row">file:{{file}}</div>
          <div class="row">data:{{data}}</div>
        </div>
      </div>
    </form>
  </div>

  <#include "/include/body-end.ftl" />


</body>

</html>