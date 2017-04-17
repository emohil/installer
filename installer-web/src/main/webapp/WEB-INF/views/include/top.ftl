<header id="header" class="navbar">
  <div class="container-fluid" ng-controller="TopController">
    <div class="row">
      <div class="col-xs-12 col-sm-4 col-md-3">
        <h4>
          <b>众联工匠管理系统</b>
        </h4>
      </div>
      <div class="col-xs-12 col-sm-8 col-md-9">
        <div class="row">
          <div class="col-xs-8 col-sm-8 col-md-4 col-lg-3 pull-right">
            <div class="pull-right">
              <a href="javascript:void(0)" class="btn btn-default">修改密码</a>
              &nbsp;<a href="logout.do" class="btn btn-default">退出登录</a>
            </div>
          </div>
          <div class="col-md-2 pull-right">
            <a class="btn btn-warning" href="indent/indentList.do?status=EXCEPTION"
              ng-hide="exceptionIndentCount <= 0">异常订单<span class="badge"
              ng-bind="exceptionIndentCount"></span></a>
          </div>
          <div class="col-xs-4 col-sm-4 col-md-4 col-lg-6 pull-right">
            <h4 class="text-nowrap">Hi，欢迎&nbsp;${Session.USER_BEAN.name1}</h4>
          </div>
        </div>
      </div>
    </div>
  </div>
</header>
<script type="text/javascript" src="static/scripts/top.js"></script>
