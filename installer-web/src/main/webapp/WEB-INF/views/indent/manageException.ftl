<#include "/include/common.ftl" />
<style type="text/css">
.grid {
  width: 100%;
  height: 140px;
}
</style>
<title>订单异常信息</title>
<#include "/include/common-entry-grid.ftl" />
<!--  -->
<script src="static/scripts/indent/manageException.js"></script>

</head>

<body ng-init="data.id='${id}'">
  <#include "/include/body-begin.ftl" />

  <div ng-controller="manageExceptionController">

    <form name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">异常详细信息</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label for="code1"
                  class="control-label col-md-4">提报人：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" id="code1"
                    name="account.name1" ng-model="data.account.name1"
                    disabled>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label for="name1"
                  class="control-label col-md-4">提报人联系方式：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" id="name1"
                    name="account.mobile" ng-model="data.account.mobile" disabled>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="form-group">
              <label class="control-label col-md-2">提报内容：</label>
              <div class="col-md-10">
                <textarea type="text" class="form-control"
                  name="content" ng-model="data.content" disabled></textarea>
              </div>
            </div>
          </div>
          <div class="row" ng-if="data.imgUrlList != ''">
            <div class="form-group">
              <label class="control-label col-md-2">提报图片：</label>
              <div class="col-md-10">
                <div class="row">
                  <div class="col-md-3"
                    ng-repeat="imgUrl in data.imgUrlList">
                    <div class="row">
                      <div class="col-md-12">
                        <img width="100%" height="200"
                          ng-src="{{imgUrl.fileUrl}}">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group" 
              ng-class="{'has-error' : form1.result.$invalid && (form1.$submitted || form1.result.$touched)}">
                <label class="control-label col-md-4">处理结果：</label>
                <div class="col-md-8">
                  <select class="form-control" name="result"
                    ng-model="data.result" required> <#list resultList as
                    result>
                    <option value="${result.value}">${result.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group" ng-if="data.result == 'OVER'"
              ng-class="{'has-error' : form1.part.$invalid && (form1.$submitted || form1.part.$touched)}">
                <label class="control-label col-md-4">责任归属：</label>
                <div class="col-md-8">
                  <select class="form-control" name="part"
                    ng-model="data.part" required> <#list partList as
                    item>
                    <option value="${item.value}">${item.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="row" ng-if="data.part == 'WORKER'">
            <div class="col-md-6">
              <div class="form-group" 
              ng-class="{'has-error' : form1.workerDeposit.$invalid && (form1.$submitted || form1.workerDeposit.$touched)}">
                <label class="control-label col-md-4">扣除工匠保证金：</label>
                <div class="col-md-8">
                <input class="form-control" name="workerDeposit"
                    ng-model="data.workerDeposit" numeric decimals="1" required/>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group" 
              ng-class="{'has-error' : form1.managerDeposit.$invalid && (form1.$submitted || form1.managerDeposit.$touched)}">
                <label class="control-label col-md-4">扣除经理人保证金：</label>
                <div class="col-md-8">
                  <input class="form-control" name="managerDeposit"
                    ng-model="data.managerDeposit" numeric decimals="1" required/>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="form-group"
              ng-class="{'has-error' : form1.idea.$invalid && (form1.$submitted || form1.idea.$touched)}">
              <label class="control-label col-md-2">处理意见：</label>
              <div class="col-md-10">
                <textarea type="text" class="form-control" name="idea"
                  ng-model="data.idea" required></textarea>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-10 col-md-offset-2">
              <button ng-if="data.executeStatus != 'AFTER'" type="submit" class="btn btn-primary"
                ng-click="save();">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
              </button>
              <span ng-if="data.executeStatus != 'AFTER'">&nbsp;&nbsp;&nbsp;</span>
              <button type="button" class="btn btn-success"
                ng-click="goBack();">
                <span class="glyphicon glyphicon-chevron-left"></span>&nbsp;返回
              </button>
            </div>
          </div>
        </div>
      </div>
    </form>
    <#include "/include/body-end.ftl" />
</body>
</html>
