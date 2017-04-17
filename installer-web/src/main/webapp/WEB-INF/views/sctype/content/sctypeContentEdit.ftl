<#include "/include/common.ftl" />
<!-- placeholder -->
<#include "/include/common-entry.ftl" />
<script src="static/scripts/sctype/content/sctypeContentEdit.js"></script>

</head>

<body ng-init="id='${id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="SctypeContentController">
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
            class="glyphicon glyphicon-triangle-bottom"></span>&nbsp;编辑服务内容</li>
        </ol>
      </div>
    </div>

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">服务类别基本信息</h3>
        </div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label for="name1" class="control-label col-md-4">服务类别编码：</label>
                <div class="col-md-7">
                  <input type="text" class="form-control"
                    ng-model="data.sctypeSort.code1" disabled />
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label for="addRate" class="control-label col-md-4">服务类别名称：</label>
                <div class="col-md-7">
                  <input type="text" class="form-control"
                    ng-model="data.sctypeSort.name1" disabled>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="panel panel-default">
        <div class="panel-heading">服务内容添加</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">服务内容编码：</label>
                <div class="col-md-7">
                  <input type="text" class="form-control" name="code1"
                    ng-model="data.code1" disabled>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.desc1.$invalid && (form1.$submitted || form1.desc1.$touched)}">
                <label class="control-label required col-md-4">服务内容明细：</label>
                <div class="col-md-7">
                  <input type="text" class="form-control" name="desc1"
                    ng-model="data.desc1" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.desc1.$invalid" required>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.unit.$invalid && (form1.$submitted || form1.unit.$touched)}">
                <label class="control-label required col-md-4">单位：</label>
                <div class="col-md-7">
                  <select id="sel" class="form-control" name="unit"
                    ng-model="data.unit" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.baseQuote.$invalid" required>
                    <#list uomList as item>
                    <option value="${item.value}"<#if
                      item.value=='${data.unit}'>selected="true"</#if>>${item.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.baseQuote.$invalid && (form1.$submitted || form1.baseQuote.$touched)}">
                <label class="control-label required col-md-4">基本报价：</label>
                <div class="col-md-7">
                  <input type="text" class="form-control"
                    name="baseQuote" ng-model="data.baseQuote"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.baseQuote.$invalid" required>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-8 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                ng-click="doSave();">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
              </button>
              &nbsp; &nbsp; &nbsp;
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