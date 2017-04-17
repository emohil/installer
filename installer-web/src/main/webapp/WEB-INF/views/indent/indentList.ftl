 <#include "/include/common.ftl" />
<title>订单管理查询</title>
<#include "/include/common-list.ftl" /> <#include
"/include/common-cityselect2.ftl" />
<script src="static/scripts/indent/indentList.js"></script>
</head>
<body ng-init="itemId='${itemId}'; mark='${mark}'; status='${status}'">
  <#include "/include/body-begin.ftl" />
  <div ng-controller="IndentListController">

    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">订单管理查询</div>
      <div class="panel-body">
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">项目名称</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.item.name1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">甲方名称</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.aparty.name1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">业主姓名</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.contact.name1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">业主电话</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.contact.mobile">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">经理人</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.managerName1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">接单工人</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.workerName1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">监理姓名</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.contact.supName1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">监理电话</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.contact.supMobile">
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <!-- comitDate begin -->
          <div class="col-md-6">
            <div class="row">
              <label class="control-label text-nowrap col-md-4 col-lg-2">提交日期</label>
              <div class="col-md-8 col-lg-10">
                <div class="row">
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup
                        ng-model="sf.comitDateBegin"
                        is-open="comitDateBegin.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openComitDateBegin()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.comitDateEnd"
                        is-open="comitDateEnd.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openComitDateEnd()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- comitDate end -->

          <!-- gotDate begin -->
          <div class="col-md-6">
            <div class="row">
              <label class="control-label text-nowrap col-md-4 col-lg-2">项目周期</label>
              <div class="col-md-8 col-lg-10">
                <div class="row">
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.gotDateBegin"
                        is-open="gotDateBegin.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openGotDateBegin()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.gotDateEnd"
                        is-open="gotDateEnd.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openGotDateEnd()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- gotDate end -->
        </div>
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">订单状态</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.status"> <#list indentStatusList
                  as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.status}'>selected="true"</#if>
                    >${item.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">执行状态</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.executeStatus"> <#list
                  indentExecuteStatusList as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.executeStatus}'>selected="true"</#if>
                    >${item.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">服务类型</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.serveType"> <#list serveTypelist
                  as item>
                  <option value="${item.code1}"<#if
                    item.code1=='${sf.serveType}'>selected="true"</#if>
                    >${item.name1}</option> </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">订单类别</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.indentType"> <#list
                  indentTypeList as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.indentType}'>selected="true"</#if>
                    >${item.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">异常状态</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.excepStatus"> <#list
                  indentExcepStatusList as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.excepStatus}'>selected="true"</#if>
                    >${item.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">评价状态</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.evaluateStatus"> <#list
                  indentEvaluateStatusList as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.evaluateStatus}'>selected="true"</#if>
                    >${item.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">上门次数</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.source"> <#list
                  sourceList as item>
                  <option value="${item.value}"<#if
                    item.value=='${sf.source}'>selected="true"</#if>
                    >${item.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">操作进度</label>
              <div class="col-md-8">
                <select id="sel" class="form-control"
                  ng-model="sf.progress"> <#list scnodeList as
                  item>
                  <option value="${item.code1}"<#if
                    item.value=='${sf.progress}'>selected="true"</#if>
                    >${item.name1}</option> </#list>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6">
            <div class="form-group" cityselect2>
              <label class="control-label text-nowrap col-md-4 col-lg-2">服务地区</label>
              <div class="col-md-8 col-lg-10">
                <div class="row">
                  <div class="col-md-4">
                    <select class="form-control" name="regionProv"
                      ng-model="sf.regionProv"
                      ng-options="prov.v as prov.n for prov in provs">
                    </select>
                  </div>
                  <div class="col-md-4" ng-hide="!cities">
                    <select class="form-control" name="regionCity"
                      ng-model="sf.regionCity"
                      ng-options="city.v as city.n for city in cities">
                    </select>
                  </div>
                  <div class="col-md-4" ng-hide="!dists">
                    <select class="form-control" name="regionDist"
                      ng-model="sf.regionDist"
                      ng-options="dist.v as dist.n for dist in dists">
                    </select>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-3 pull-right">
            <button id="search" class="btn btn-warning pull-right"
              ng-click="query()">
              <span class="glyphicon glyphicon-search"></span>&nbsp;筛选
            </button>
          </div>
        </div>
      </div>
    </div>
    <div class="panel panel-default">
      <table class="table table-bordered table-striped">
        <thead>
          <tr>
            <th>订单ID</th>
            <th>业主姓名</th>
            <th>甲方名称</th>
            <th>订单类别</th>
            <th>执行状态</th>
            <th>接单日期</th>
            <th>抢单工人</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td><font ng-if="data.status == 'CANCEL'" color="red">{{data.code1}}</font>
              <font ng-if="data.status == 'EXCEPTION'" color="orange">{{data.code1}}</font>
              <font ng-if="data.status == 'OVER'" color="green">{{data.code1}}</font>
              <font ng-if="data.status == 'NORMAL'">{{data.code1}}</font></td>
            <td><a href="indent/indentEdit.do?id={{data.id}}">{{data.contact.name1}}</a></td>
            <td>{{data.aparty.name1}}</td>
            <td><a
              href="indentNode/indentNodeTree.do?id={{data.id}}">{{data.serveTypeDisp}}</a></td>
            <td>{{data.executeStatusDisp}}</td>
            <td>{{data.serviceDate}}</td>
            <td><span ng-if="data.executeStatus != 'BEFORE'">{{data.workerName1}}</span></td>
            <!-- tableA样式只针对 操作项 
            <a class="btn btn-success btn-xs"><span
                class="glyphicon glyphicon-pause"></span>暂停</a>-->
            <td>
            <span ng-if="data.releaseStatus == 'UNRELEASED'"><a
                class="btn btn-success btn-xs"
                ng-click="pushIndent(data.id);"><span
                  class="glyphicon glyphicon-send"></span>&nbsp;发布订单</a></span>
                  <span ng-if="data.executeStatus == 'AFTER' && data.evaluateStatus == 'EVALUATED'"><a
                class="btn btn-success btn-xs"
                href="indentEvaluate/indentEvaluateMsg.do?indentId={{data.id}}"><span
                  class="glyphicon glyphicon-eye-open"></span>查看评价</a></span>
                  <span ng-if="data.status == 'OVER' && data.newCode1 == ''"><a
                class="btn btn-success btn-xs" ng-click="extraIndent(data.id);"><span
                  class="glyphicon glyphicon-plus"></span>二次上门</a></span>
                  <span ng-if="data.status == 'OVER' && data.newCode1 != ''"><a href="indent/indentEdit.do?code1={{data.newCode1}}">{{data.newCode1}}</a></span>
                  </td>
          </tr>
        </tbody>
      </table>
      <div class="panel-footer">
        <!--  -->
        <#include "/include/pagination.ftl" />
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />

</body>
</html>