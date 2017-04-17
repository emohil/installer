<#include "/include/common.ftl" /> <#include "/include/common-entry.ftl"/>
<!-- -->
<#include "/include/common-cityselect2.ftl" />
<script src="static/scripts/core/ifu-workerselect.js"></script>
<script type="text/javascript" src="static/scripts/indent/indentAdd.js"></script>
</head>
<body>
  <#include "/include/body-begin.ftl" />
  <div ng-controller="IndentController">
    <form id="inputForm" name="form1" class="form-horizontal" novalidate
      enctype="multipart/form-data">
      <div class="panel panel-default">
        <div class="panel-heading">订单基本信息</div>
        <div class="panel-body">
          <#if data??>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">甲方名称：</label>
                <div class="col-md-8">
                  <input type="text" value="${data.aparty.name1}"
                    class="form-control" disabled> <input
                    type="hidden" name="apartyId"
                    ng-init="data.apartyId='${data.aparty.id}'"
                    ng-model="data.apartyId">
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">项目名称：</label>
                <div class="col-md-8">
                  <input type="text" value="${data.name1}"
                    class="form-control" disabled> <input
                    type="hidden" name="itemId"
                    ng-init="data.itemId='${data.id}'"
                    ng-model="data.itemId">
                </div>
              </div>
            </div>
          </div>
          <#else>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.apartyId.$invalid && (form1.$submitted || form1.apartyId.$touched)}">
                <label class="control-label required col-md-4">甲方名称：</label>
                <div class="col-md-8">
                  <input type="text" name="apartyId"
                    ng-model="data.apartyIdDisp" class="form-control"
                    uib-typeahead="pair.text as pair.text for pair in doTypeaheadApartys($viewValue)"
                    typeahead-on-select="onTypeaheadApartySelect($item, $model, $label)"
                    typeahead-min-length="2"
                    typeahead-loading="loadingTest1"
                    typeahead-no-results="noResults1" required>
                  <i ng-show="loadingTest1"
                    class="glyphicon glyphicon-refresh"></i>
                  <div ng-show="noResults1">
                    <i class="glyphicon glyphicon-remove"></i>没有找到相关信息
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.itemId.$invalid && (form1.$submitted || form1.itemId.$touched)}">
                <label class="control-label required col-md-4">项目名称：</label>
                <div class="col-md-8">
                  <input type="text" name="itemId" ng-model="itemIdDisp"
                    class="form-control"
                    uib-typeahead="pair.text as pair.text for pair in doTypeaheadItems($viewValue)"
                    typeahead-on-select="onTypeaheadItemSelect($item, $model, $label)"
                    typeahead-min-length="2"
                    typeahead-loading="loadingTest2"
                    typeahead-no-results="noResults2" required>
                  <i ng-show="loadingTest2"
                    class="glyphicon glyphicon-refresh"></i>
                  <div ng-show="noResults2">
                    <i class="glyphicon glyphicon-remove"></i>没有找到相关信息
                  </div>
                </div>
              </div>
            </div>
          </div>
          </#if>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label required col-md-4">服务类别：</label>
                <div class="col-md-8">
                  <select id="sel" class="form-control" name="serceType"
                    ng-init="data.serveType='${serveType}'"
                    ng-model="data.serveType" ng-change="changeTarget()">
                    <#list serveTypelist as item>
                    <option value="${item.code1}">${item.name1}</option>
                    </#list>
                  </select>
                  
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.durationTime.$invalid && (form1.$submitted || form1.durationTime.$touched)}">
                <label class="control-label required col-md-4">订单服务时长:</label>
                <div class="col-md-8">
                  <input  type="text" class="form-control"
                    name="durationTime" ng-init="data.durationTime='72'" ng-model="data.durationTime"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.durationTime.$invalid"
                    numeric decimals="0" required>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
              ng-class="{'has-error' : form1.indentSource.$invalid && (form1.$submitted || form1.indentSource.$touched)}">
                <label class="control-label required col-md-4">订单类型：</label>
                <div class="col-md-8">
                  <select id="sel" class="form-control" name="indentSource"
                    ng-model="data.indentSource">
                    <#list indentSourceList as item>
                    <option value="${item.value}">${item.text}</option>
                    </#list>
                  </select>
                </div>
              </div>
            </div>
            <div class="col-md-6">
            </div>
          </div>
        </div>
      </div>
      <#if serveType!="">
      <div class="panel panel-default">
        <div class="panel-heading">订单内容</div>
        <div class="panel-body">
          <#list serveTypeList as type> <#if type.code1==serveType>
          <div class="form-group">
            <label class="control-label col-md-2">${type.name1}</label>
            <div class="col-md-9">
              <table class="table table-border">
                <#list type.sortList as sort>
                <tr>
                  <td rowspan="${(sort.contentList.size()/3)?ceiling}">${sort.name1}</td>
                  <#list sort.contentList as content> <#if
                  sort.id==content.sctypeSortId>
                  <td><input type="checkbox" ng-change="judge_drawing_top_limit('${content.id}');"
                    ng-model="data.indentPriceDto.indentPrice_${content.id}_checked">${content.desc1}<input
                    class="form-control" type="text" name="counts"
                    ng-model="data.indentPriceDto.indentPrice_${content.id}_counts"
                    style="width: 50px; display: inline-block; margin: 0 15px;" numeric decimals="1">${content.unitDisp}
                    <input type="hidden"
                    ng-init="data.indentPriceDto.indentPrice_${content.id}_code1='${content.id}'">
                  </td> <#if (content_index+1)%3 == 0>
                </tr>
                <tr></#if> </#if> </#list>
                </tr>
                </#list>
              </table>
            </div>
          </div>
          </#if> </#list>
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
              <div class="form-group"
                ng-class="{'has-error' : form1.name1.$invalid && (form1.$submitted || form1.name1.$touched)}">
                <label class="control-label required col-md-4">业主名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="name1"
                    ng-model="data.contact.name1" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.name1.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.mobile.$invalid && (form1.$submitted || form1.mobile.$touched)}">
                <label class="control-label required col-md-4">业主电话：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control" name="mobile" 
                    ng-pattern="/(^13\d{9}$)|(^14)[5,7]\d{8}$|(^15[0,1,2,3,5,6,7,8,9]\d{8}$)|(^17)[6,7,8]\d{8}$|(^18\d{9}$)/"
                    ng-model="data.contact.mobile" uib-tooltip="此项为必填项"
                    tooltip-enable="form1.mobile.$invalid" required>
                    <div ng-show="form1.mobile.$dirty && form1.mobile.$invalid"><font size="2" color="red">电话格式不正确</font></div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.supName1.$invalid && (form1.$submitted || form1.supName1.$touched)}">
                <label class="control-label required col-md-4">监理名称：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    name="supName1" ng-model="data.contact.supName1"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.supName1.$invalid" required>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group"
                ng-class="{'has-error' : form1.supMobile.$invalid && (form1.$submitted || form1.supMobile.$touched)}">
                <label class="control-label required col-md-4">监理电话：</label>
                <div class="col-md-8">
                  <input type="text" class="form-control"
                    ng-pattern="/(^13\d{9}$)|(^14)[5,7]\d{8}$|(^15[0,1,2,3,5,6,7,8,9]\d{8}$)|(^17)[6,7,8]\d{8}$|(^18\d{9}$)/"
                    name="supMobile" ng-model="data.contact.supMobile"
                    uib-tooltip="此项为必填项"
                    tooltip-enable="form1.supMobile.$invalid" required>
                    <div ng-show="form1.supMobile.$dirty && form1.supMobile.$invalid"><font size="2" color="red">电话格式不正确</font></div>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group" 
            ng-class="{'has-error' : form1.regionProv.$invalid && (form1.$submitted || form1.regionProv.$touched)}" cityselect2>
            <label class="control-label required col-md-2">服务地址：</label>
            <div class="col-md-2">
              <select class="form-control" name="regionProv"
                ng-model="data.regionProv"
                ng-options="prov.v as prov.n for prov in provs" required>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!cities"  
            ng-class="{'has-error' : form1.regionCity.$invalid && (form1.$submitted || form1.regionCity.$touched)}">
              <select class="form-control" name="regionCity"
                ng-model="data.regionCity"
                ng-options="city.v as city.n for city in cities" required>
              </select>
            </div>
            <div class="col-md-2" ng-hide="!dists"  
            ng-class="{'has-error' : form1.regionDist.$invalid && (form1.$submitted || form1.regionDist.$touched)}">
              <select class="form-control" name="regionDist"
                ng-model="data.regionDist"
                ng-options="dist.v as dist.n for dist in dists" required>
              </select>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.detailAddr1.$invalid && (form1.$submitted || form1.detailAddr1.$touched)}">
            <label class="control-label required col-md-2">详细地址：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control"
                name="detailAddr1" ng-model="data.contact.detailAddr1"
                uib-tooltip="此项为必填项"
                tooltip-enable="form1.detailAddr1.$invalid" required>
              </textarea>
            </div>
          </div>
          <div class="form-group"
            ng-class="{'has-error' : form1.demand.$invalid && (form1.$submitted || form1.demand.$touched)}">
            <label class="control-label required col-md-2">特殊需求：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control" name="demand"
                ng-model="data.contact.demand" required></textarea>
            </div>
          </div>
          <div class="form-group" ng-show="data.regionDist" workerselect>
            <label class="control-label col-md-2"> 指派工匠：</label>
            <div class="col-md-1">
              <input type="checkbox" ng-model="data.indentAssignSelected"/>
            </div>
            <div class="col-md-2" ng-show="data.indentAssignSelected" >
              <select class="form-control"
                name="managerId" ng-model="data.managerId"
                ng-options="manager.v as manager.n for manager in managers" >
              </select>
            </div>
            <div class="col-md-2" ng-show="data.indentAssignSelected" >
              <select class="form-control" 
                 name="workerId" ng-model="data.workerId" 
                 ng-options="worker.v as worker.n for worker in workers" >
              </select>
            </div>
          </div>
          <div class="form-group" ng-show="data.serveType != 'C'"
            ng-class="{'has-error' : form1.imgFiles.$invalid && (form1.$submitted || form1.imgFiles.$touched)}">
            <label class="control-label required col-md-2">安装图纸：</label>
            <div class="col-md-10">
              <input type="file" name="imgFiles"
                  file-model="file.imgFiles" accept="image/*"
                   multiple/>
            </div>
          </div>
          <div class="form-group">
            <div class="col-md-10 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                fng-click="save();">
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