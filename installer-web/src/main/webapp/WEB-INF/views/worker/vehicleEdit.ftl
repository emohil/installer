<#include "/include/common.ftl" /> <#include "/include/common-entry.ftl"
/>
<!--  -->
<#include "/include/common-cityselect.ftl" />
<title>审核车辆信息</title>
<#include "/include/photoswipe.ftl" />
<script type="text/javascript"
  src="static/scripts/account/vehicleEdit.js"></script>
</head>
<body ng-init="data.id='${id}'">
  <#include "/include/body-begin.ftl" />
  <div ng-controller="vehicleEditController">
    <form id="inputForm" name="form1" class="form-horizontal" novalidate
      enctype="multipart/form-data">
      <div class="panel panel-default">
        <div class="panel-heading">工匠车辆信息</div>
        <div class="panel-body">
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">姓名：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.account.name1}}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">身份证号：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.account.idNum}}</p>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">联系方式：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.account.mobile}}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">现住址：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.account.addr1}}</p>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">服务区域：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.serviceRegion}}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">归属类型：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.holdWayDisp}}</p>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">车辆类型：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.vehicleDisp}}</p>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">车辆牌照：</label>
                <div class="col-md-8">
                  <p class="form-control-static">{{data.carNumber}}</p>
                </div>
              </div>
            </div>
            <div class="col-md-6">
            </div>
          </div>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">驾驶证：</label>
                <div class="my-gallery" itemscope>
                  <div class="col-md-8">
                    <figure itemprop="associatedMedia" itemscope>
                      <a href="{{data.licence}}" itemprop="contentUrl"
                        data-size="1024x683"><img
                        ng-src="{{data.licence}}" itemprop="thumbnail"
                        alt="驾驶证照片" width="250" height="120" /> </a>
                    </figure>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group">
                <label class="control-label col-md-4">行驶证：</label>
                <div class="my-gallery" itemscope>
                  <div class="col-md-8">
                    <figure itemprop="associatedMedia" itemscope>
                      <a href="{{data.licence1}}" itemprop="contentUrl"
                        data-size="1024x683"><img
                        ng-src="{{data.licence1}}" itemprop="thumbnail"
                        alt="行驶证照片" width="250" height="120" /> </a>
                    </figure>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="form-group">
            <label class="control-label col-md-2">审核意见：</label>
            <div class="col-md-4">
              <select class="form-control" ng-model="data.vehicleStatus">
                <#list checkStatusList as item>
                <option value="${item.value}">${item.text}</option>
                </#list>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-md-4 col-md-offset-2">
              <button type="submit" class="btn btn-primary"
                ng-click="save();">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;提交
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