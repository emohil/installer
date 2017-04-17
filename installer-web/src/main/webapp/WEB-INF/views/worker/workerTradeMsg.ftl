<#include "/include/common.ftl" /> <#include "/include/common-entry.ftl"
/>
<!--  -->
<#include "/include/common-cityselect.ftl" />
<title>工匠提现申请明细</title>
<#include "/include/photoswipe.ftl" />
<script type="text/javascript"
  src="static/scripts/account/workerTradeEdit.js"></script>
</head>
<body ng-init="data.id='${id}'">
  <#include "/include/body-begin.ftl" />
  <div ng-controller="workerTradeEditController">
    <form id="inputForm" name="form1" class="form-horizontal" novalidate
      enctype="multipart/form-data">
      <div class="panel panel-default">
        <div class="panel-heading">提现信息</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">工匠姓名：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.workerName}}</p>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">银行名称：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.bankCard.bankName}}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">提现金额：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.curAmt}}</p>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">银行卡号：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.bankCard.cardNo}}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">手续费：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.factorage}}</p>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">申请时间：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.createDate}}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">应转金额：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.actualAmt}}</p>
                </div>
              </div>
            </div>
          </div>

          <div class="form-group">
            <label class="control-label col-md-2">反馈结果：</label>
            <div class="col-md-10">
              <textarea type="text" class="form-control"
                ng-model="data.desc1" disabled>{{data.desc1}}</textarea>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label col-md-2">流水号：</label>
            <div class="col-md-4">
              <input type="text" class="form-control" name="code1"
                ng-model="data.code1" disabled>
            </div>
          </div>

          <div class="form-group">
            <label class="control-label col-md-2">处理状态：</label>
            <div class="col-md-4">
              <p class="form-control-static">{{data.tradeStatusDisp}}</p>
            </div>
          </div>
          <div class="form-group">
            <div class="col-md-4 col-md-offset-2">
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
  <!-- photoswip 代码 -->
  <div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="pswp__bg"></div>
    <div class="pswp__scroll-wrap">
      <div class="pswp__container">
        <div class="pswp__item"></div>
        <div class="pswp__item"></div>
        <div class="pswp__item"></div>
      </div>
      <div class="pswp__ui pswp__ui--hidden">
        <div class="pswp__top-bar">
          <div class="pswp__counter"></div>
          <button class="pswp__button pswp__button--close"
            title="Close (Esc)"></button>
          <button class="pswp__button pswp__button--zoom"
            title="Zoom in/out"></button>
          <div class="pswp__preloader">
            <div class="pswp__preloader__icn">
              <div class="pswp__preloader__cut">
                <div class="pswp__preloader__donut"></div>
              </div>
            </div>
          </div>
        </div>
        <div
          class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
          <div class="pswp__share-tooltip"></div>
        </div>
        <button class="pswp__button pswp__button--arrow--left"
          title="Previous (arrow left)"></button>
        <button class="pswp__button pswp__button--arrow--right"
          title="Next (arrow right)"></button>
        <div class="pswp__caption">
          <div class="pswp__caption__center"></div>
        </div>
      </div>
    </div>
  </div>
  <#include "/include/body-end.ftl" />
</body>
</html>