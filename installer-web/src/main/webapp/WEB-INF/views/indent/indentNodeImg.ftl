<#include "/include/common.ftl" />
<title></title>
<#include "/include/common-entry-grid.ftl" />
<!--  -->
<script type="text/javascript"
  src="static/scripts/indent/indentNodeImg.js"></script>
</head>
<body ng-init="id='${id}'">
  <#include "/include/body-begin.ftl" />
  <div ng-controller="IndentNodeImgController">
    <form id="inputForm" name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading"></div>
        <div class="panel-body">
          <#list indentNodeStepItems as indentNodeStepItem>
          <div class="row">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-2">${indentNodeStepItem.desc1}</label>
              <div>
                <div class="col-md-10">
                  <div class="row">
                    <#list indentNodeStepItem.imgUrlList as imgUrl>
                    <div class="col-md-4" style="height: 220px">
                      <div class="row">
                        <div class="col-md-12">
                          <img width="100%" height="200" src="${imgUrl.fileUrl}">
                        </div>
                      </div>
                    </div>
                    </#list>
                  </div>
                </div>
              </div>
            </div>
          </div>
          </#list>
          <div class="row">
            <div class="col-md-12">
              <button type="button" class="btn btn-success pull-right"
                ng-click="goBack();">
                <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
              </button>
            </div>
          </div>
    </form>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>