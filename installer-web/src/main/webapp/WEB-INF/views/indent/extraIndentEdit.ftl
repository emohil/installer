<#include "/include/common.ftl" />
<title></title>
<#include "/include/common-entry-grid-debug.ftl" />
<!--  -->
<#include "/include/common-cityselect2.ftl" />
<script type="text/javascript" src="static/scripts/indent/extraIndentEdit.js"></script>
</head>
<body ng-init="data.id='${data.id}'">
  <#include "/include/body-begin.ftl" />
  <div ng-controller="IndentController">
    <form id="inputForm" name="form1" class="form-horizontal" novalidate>
      <div class="panel panel-default">
        <div class="panel-heading">订单基本信息</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">甲方名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    value="${apartyName1}" disabled> <input
                    type="hidden" name="apartyId"
                    ng-model="data.apartyId">
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">项目名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    value="${itemName1}" disabled> <input
                    type="hidden" name="itemId" ng-model="data.itemId">
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
            <div class="form-group">
                <label class="control-label required col-md-4">订单类型：</label>
                <div class="col-md-8">
                  <select id="sel" class="form-control" name="indentSource"
                    ng-model="data.indentSource" disabled>
                    <#list indentSourceList as item>
                    <option value="${item.value}">${item.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.durationTime.$invalid && (form1.$submitted || form1.durationTime.$touched)}">
                <label class="control-label required col-md-4">订单服务时长：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="durationTime" ng-model="data.durationTime" disabled>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-2">订单备注：</label>
            <div class="col-md-10">
              <textarea class="form-control"
                ng-model="data.comment" disabled></textarea>
            </div>
          </div>
        </div>
      </div>
      <#if data??>
      <div class="panel panel-default">
        <div class="panel-heading">订单内容</div>
        <div class="panel-body">
          <#list serveTypeList as type> <#if type.code1==serveType>
          <div class="form-group">
            <label class="control-label col-md-3">${type.name1}</label>
            <div class="col-md-9">
              <table class="table table-border">
                <#list type.sortList as sort>
                <tr>
                  <td rowspan="${(sort.contentList.size()/3)?ceiling}">${sort.name1}</td>
                  <#list sort.contentList as content> <#if
                  sort.id==content.sctypeSortId>
                  <td><input type="checkbox"
                    ng-model="data.indentPriceDto.indentPrice_${content.id}_checked" disabled>${content.desc1}<input
                    class="form-control" type="text" name="counts"
                    ng-model="data.indentPriceDto.indentPrice_${content.id}_counts"
                    style="width: 50px; display: inline-block; margin: 0 15px;" disabled>${content.unitDisp}
                    <input type="hidden" name="code1"
                    ng-init="data.indentPriceDto.indentPrice_${content.id}_code1='${content.id}'">
                  </td> <#if (content_index+1)%3 == 0>
                </tr>
                <tr></#if> </#if> </#list>
                </tr>
                </#list>
              </table>
            </div>
          </div>
          </#if>
          </#list>
        </div>
      </div>
      </#if>
      <div class="panel panel-default" ng-if="data.serveType == 'T'">
        <div class="panel-heading">提货信息</div>
        <div class="panel-body">
          <div class="form-group"
            ng-class="{'has-error' : form1.regionProv1.$invalid && (form1.$submitted || form1.regionProv1.$touched)}" cityselect2>
            <label class="control-label required col-md-2">提货地址：</label>
            <div class="col-md-2">
              <select class="form-control" name="regionProv1"
                ng-model="data.indentFreight.regionProv"
                ng-options="prov.v as prov.n for prov in provs" required>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!cities"
            ng-class="{'has-error' : form1.regionCity1.$invalid && (form1.$submitted || form1.regionCity1.$touched)}">
              <select class="form-control" name="regionCity1"
                ng-model="data.indentFreight.regionCity"
                ng-options="city.v as city.n for city in cities" required>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!dists" 
            ng-class="{'has-error' : form1.regionDist1.$invalid && (form1.$submitted || form1.regionDist1.$touched)}">
              <select class="form-control" name="regionDist1"
                ng-model="data.indentFreight.regionDist"
                ng-options="dist.v as dist.n for dist in dists" required>
              </select>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.addr1.$invalid && (form1.$submitted || form1.addr1.$touched)}">
            <label class="control-label required col-md-2">详细地址：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control" name="addr1"
                ng-model="data.indentFreight.addr1" uib-tooltip="此项为必填项"
                tooltip-enable="form1.addr1.$invalid" required>
                </textarea>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.contacts.$invalid && (form1.$submitted || form1.contacts.$touched)}">
                <label class="control-label required col-md-4">提货联系人：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="contacts" ng-model="data.indentFreight.contacts"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.contacts.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.mobile1.$invalid && (form1.$submitted || form1.mobile1.$touched)}">
                <label class="control-label required col-md-4">联系电话：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="mobile1" 
                    ng-pattern="/(^13\d{9}$)|(^14)[5,7]\d{8}$|(^15[0,1,2,3,5,6,7,8,9]\d{8}$)|(^17)[6,7,8]\d{8}$|(^18\d{9}$)/"
                    ng-model="data.indentFreight.mobile" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.mobile1.$invalid" required>
                    <div ng-show="form1.mobile1.$dirty && form1.mobile1.$invalid"><font size="2" color="red">电话格式不正确</font></div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.code1.$invalid && (form1.$submitted || form1.code1.$touched)}">
                <label class="control-label required col-md-4">货运单号：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="code1"
                    ng-model="data.indentFreight.code1" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.code1.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.packageNum.$invalid && (form1.$submitted || form1.packageNum.$touched)}">
                <label class="control-label required col-md-4">件数：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="packageNum" ng-model="data.indentFreight.packageNum"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.packageNum.$invalid" numeric decimals="0" required>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.kgs.$invalid && (form1.$submitted || form1.kgs.$touched)}">
                <label class="control-label required col-md-4">毛重：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="kgs"
                    ng-model="data.indentFreight.kgs" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.kgs.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.carModel.$invalid && (form1.$submitted || form1.carModel.$touched)}">
                <label class="control-label required col-md-4">要求车型：</label>
                <div class="col-md-8">
                  <select id="sel" class="form-control" name="carModel"
                    ng-model="data.indentFreight.carModel" required>
                    <#list vehicleList as item>
                    <option value="${item.value}">${item.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.comment.$invalid && (form1.$submitted || form1.comment.$touched)}">
            <label class="control-label required col-md-2">备注：</label>
            <div class="col-md-10">
              <textarea class="form-control" name="comment"
                ng-model="data.indentFreight.comment" required></textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">业主联系信息</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">业主名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="name1"
                    ng-model="data.contact.name1" disabled>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">业主电话：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="mobile"
                    ng-model="data.contact.mobile" disabled>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">监理名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="supName1" ng-model="data.contact.supName1" disabled>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">监理电话：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="supMobile" ng-model="data.contact.supMobile" disabled>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group" cityselect2>
            <label class="control-label col-md-2">服务地址：</label>
            <div class="col-md-2">
              <select class="form-control" name="regionProv"
                ng-model="data.regionProv"
                ng-options="prov.v as prov.n for prov in provs" disabled>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!cities">
              <select class="form-control" name="regionCity"
                ng-model="data.regionCity"
                ng-options="city.v as city.n for city in cities" disabled>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!dists">
              <select class="form-control" name="regionDist"
                ng-model="data.regionDist"
                ng-options="dist.v as dist.n for dist in dists" disabled>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label required col-md-2">详细地址：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control"
                name="detailAddr1" ng-model="data.contact.detailAddr1" disabled>
              </textarea>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-2">特殊需求：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control" name="demand"
                ng-model="data.contact.demand" disabled></textarea>
            </div>
          </div>
          <div class="form-group" ng-if="data.serveType != 'C'">
            <label class="control-label required col-md-2">安装图纸：</label>
            <div class="col-md-10">
            <#list data.dwgImgList as item>
              <img src="${item.fileUrl}" height="100" width="100">
            </#list>
            </div>
          </div>
          <div class="form-group">
            <div class="col-md-10 col-md-offset-2">
              <button type="button" class="btn btn-primary"
                ng-click="doSave();">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;保存
              </button>&nbsp;&nbsp;
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