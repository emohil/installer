<#include "/include/common.ftl" />
<link rel="stylesheet" href="static/css/homepage.css" type="text/css" />
<script src="static/angular/angular.js"></script>
<script type="text/javascript">
angular.module('app', []);
</script>
<title>众联工匠管理系统</title>
</head>
<body>
  <#include "/include/body-begin.ftl" />

  <div class="row">
    <div class="col-md-6">
      <div class="panel panel-default">
        <div class="listName">甲方</div>
        <div class="listInfo">
          <a class="odd">
            <div class="infoTitle">累计甲方</div>
            <div class="infoDetail">
              <span class="toInt">${totalAparty}</span>家
            </div>
          </a> <a>
            <div class="infoTitle">累计项目</div>
            <div class="infoDetail">
              <span class="toInt">${totalItem}</span>项
            </div>
          </a> <a class="odd">
            <div class="infoTitle">累计充值</div>
            <div class="infoDetail">
              &yen;<span>88888.88</span>元
            </div>
          </a> <a>
            <div class="infoTitle">当前余额</div>
            <div class="infoDetail">
              &yen;<span>88888.88</span>元
            </div>
          </a>
        </div>

      </div>
    </div>

    <div class="col-md-6">
      <div class="panel panel-default">
        <div class="listName">订单</div>
        <div class="listInfo">
          <a class="odd">
            <div class="infoTitle">累计订单</div>
            <div class="infoDetail">
              <span>${totalIndent}</span>单
            </div>
          </a> <a>
            <div class="infoTitle">累计完成</div>
            <div class="infoDetail">
              <span>${indentAfter}</span>单
            </div>
          </a> <a class="odd">
            <div class="infoTitle">执行中</div>
            <div class="infoDetail">
              <span>${indentCentre}</span>单
            </div>
          </a> <a>
            <div class="infoTitle">待接单</div>
            <div class="infoDetail">
              <span>${indentBefore}</span>单
            </div>
          </a>
        </div>

      </div>
    </div>

  </div>

  <div class="row">
    <div class="col-md-6">
      <div class="panel panel-default">
        <div class="listName">经理人</div>
        <div class="listInfo">
          <a class="odd">
            <div class="infoTitle">经理人总数</div>
            <div class="infoDetail">
              <span>${totalManager}</span>人
            </div>
          </a> <a>
            <div class="infoTitle">累计获得佣金</div>
            <div class="infoDetail">
              &yen;<span>${totalManagerTrade}</span>元
            </div>
          </a> <a class="odd">
            <div class="infoTitle">累计发展工人</div>
            <div class="infoDetail">
              <span>${totalWorker}</span>人
            </div>
          </a> <a>
            <div class="infoTitle">累计完成项目</div>
            <div class="infoDetail">
              <span>${afteritems}</span>项
            </div>
          </a>
        </div>
      </div>
    </div>

    <div class="col-md-6">
      <div class="panel panel-default">

        <div class="listName">工人</div>
        <div class="listInfo">
          <a class="odd">
            <div class="infoTitle">累计工人总和</div>
            <div class="infoDetail">
              <span>${totalWorker}</span>人
            </div>
          </a> <a>
            <div class="infoTitle">未通过审核工人</div>
            <div class="infoDetail">
              <span>${notpassWorkers}</span>人
            </div>
          </a> <a class="odd">
            <div class="infoTitle">累计完成订单</div>
            <div class="infoDetail">
              <span>${indentAfter}</span>单
            </div>
          </a> <a>
            <div class="infoTitle">累计获得服务费</div>
            <div class="infoDetail">
              &yen;<span>${totalWorkerTrade}</span>元
            </div>
          </a>
        </div>
      </div>
    </div>
  </div>
  
  <#include "/include/body-end.ftl" />
</body>
</html>