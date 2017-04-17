<#include "/include/common.ftl" />
<title>项目管理查询</title>
<#include "/include/common-list.ftl" />
<script type="text/javascript"
  src="${base}/static/My97DatePicker/WdatePicker.js"></script>
<script src="static/scripts/item/itemList.js"></script>
</head>
<body>
  <#include "/include/body-begin.ftl" />

  <div ng-controller="ItemListController">

    <div class="panel panel-default form-horizontal">
      <div class="panel-heading">项目管理查询</div>
      <div class="panel-body">
        <div class="row">
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">项目名称</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.name1">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">甲方名称</label>
              <div class="col-md-8">
                <input type="text" class="form-control"
                  ng-model="sf.apartyIdDisp">
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">项目状态</label>
              <div class="col-md-8">
                <select class="form-control" ng-model="sf.status">
                  <#list itemStatusList as s>
                  <option value="${s.value}"<#if
                    s.value=='${sf.status}'>selected="true"</#if>
                    >${s.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
          <div class="col-md-6 col-lg-3">
            <div class="form-group">
              <label class="control-label text-nowrap col-md-4">结算状态</label>
              <div class="col-md-8">
                <select class="form-control" ng-model="sf.payStatus">
                  <#list payStatusList as ps>
                  <option value="${ps.value}"<#if
                    ps.value=='${sf.payStatus}'>selected="true"</#if>
                    >${ps.text}</option> </#list>
                </select>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <label class="control-label text-nowrap col-md-4 col-lg-2">提交日期</label>
              <div class="col-md-8 col-lg-10">
                <div class="row">
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.crtDateBegin"
                        is-open="crtDateBegin.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openCrtDateBegin()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.crtDateEnd"
                        is-open="crtDateEnd.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openCrtDateEnd()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <label class="control-label text-nowrap col-md-4 col-lg-2">项目周期</label>
              <div class="col-md-8 col-lg-10">
                <div class="row">
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.beginDate"
                        is-open="beginDate.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openBeginDate()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                  <div class="col-md-6">
                    <p class="input-group">
                      <input type="text" class="form-control"
                        uib-datepicker-popup ng-model="sf.overDate"
                        is-open="overDate.opened"
                        datepicker-options="dateOptions"
                        ng-required="true" /> <span
                        class="input-group-btn">
                        <button type="button" class="btn btn-default"
                          ng-click="openOverDate()">
                          <i class="glyphicon glyphicon-calendar"></i>
                        </button>
                      </span>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-md-3 pull-right">
            <button class="btn btn-warning pull-right"
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
            <th width="160px">项目ID</th>
            <th>项目名称</th>
            <th width="200px">服务类型</th>
            <th width="120px">下单量/单</th>
            <th width="120px">接单量/单</th>
            <th width="100px">到期日</th>
            <th width="100px">项目状态</th>
            <th width="120px">操作</th>
          </tr>
        </thead>
        <tbody ng-repeat="data in rows">
          <tr>
            <td>{{data.code1}}</td>
            <td><a href="item/itemEdit.do?id={{data.id}}">{{data.name1}}</a></td>
            <td>{{data.serveType}}</td>
            <td style="text-align:center"><a href="indent/indentList.do?itemId={{data.id}}">{{data.actualAmt}}</a></td>
            <td style="text-align:center"><a href="indent/indentList.do?itemId={{data.id}}&&mark=1">{{data.receiveAmt}}</a></td>
            <td>{{data.overDate}}</td>
            <td>{{data.statusDisp}}</td>
            <td><a class="btn btn-success btn-xs"
              href="indent/indentAdd.do?id={{data.id}}"><span
                class="glyphicon glyphicon-plus"></span>&nbsp;订单</a></td>
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