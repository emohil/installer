<#include "/include/common.ftl" />
<#include "/include/common-entry-debug.ftl" />
<title>加入众联工匠</title>
<script src="static/js/jquery.min.js"></script>
<script src="static/scripts/invite/invite.js"></script>
<style>
body, header, form, p, section, div, input, img {
	padding: 0;
	margin: 0;
	border: none;
	list-style: none;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

body {
	position: relative;
	padding-bottom: 54px;
	background-color: #fafafa;
	color: #fff;
	font-size: 16px;
	text-align: center;
}

.left {
	float: left;
}

.right {
	float: right;
}

.displayNone {
	display: none;
}

header {
	padding: 30px 0 27px;
}

header div {
	width: 84%;
	margin: 0 auto;
}

form .join {
	position: relative;
	width: 84%;
	height: 45px;
	margin: 0 auto;
}

form .join>div {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 45px;
}

form .join .btn div {
	width: 100%;
	height: 45px;
	line-height: 45px;
	background-color: #5890FF;
	border-radius: 3px;
}

form .join .phone {
	overflow: hidden;
}

form .join .phone>div {
	height: 45px;
}

form .join .phone .left {
	width: 72%;
}

form .join .phone .left input {
	width: 100%;
	height: 45px;
	line-height: 45px;
	padding: 0 12px;
	font-size: 16px;
	color: #9B9B9B;
	background-color: #F1F1F1;
}

form .join .phone .right {
	width: 24%;
	line-height: 45px;
	background-color: #DCDEDE;
}
/*提交可点击*/
form .join .phone .right.can_btn {
	background-color: #5890FF;
}

form section {
	margin: 30px 0 42px;
	padding-bottom: 60px;
	font-size: 30px;
	background-color: #3B5999;
}

form section p {
	padding: 54px 0 21px;
}

form section p span {
	color: #F5A623;
}

form section div {
	width: 66.66%;
	margin: 0 auto;
}
</style>
</head>
<body ng-controller="invitePartnerController" ng-init="data.accountId='${accountId}'">
  <header>
    <div>
      <img src="static/images/invite/1.png" width="100%">
    </div>
  </header>

  <form>
    <div class="join">
      <div class="btn">
        <div>立即加入</div>
      </div>
      <div class="phone displayNone">
        <div class="left">
          <input type="tel" ng-model="data.mobile" maxlength="11" />
        </div>
        <div class="right" ng-click="save();">提交</div>
      </div>
    </div>
    <section>
      <p>有单抢 有钱赚</p>
      <div>
        <img src="static/images/invite/2.png" width="100%">
      </div>
      <p>有组织 有保障</p>
      <div>
        <img src="static/images/invite/3.png" width="100%">
      </div>
      <p>
        已有<span>${nums}</span>名小伙伴加入
      </p>
      <div>
        <img src="static/images/invite/4.png" width="100%">
      </div>
    </section>
    <div class="join">
      <div class="btn">
        <div>立即加入</div>
      </div>
      <div class="phone displayNone">
        <div class="left">
          <input type="tel" ng-model="data.mobile" maxlength="11" />
        </div>
        <div class="right" ng-click="save();">提交</div>
      </div>
    </div>
  </form>


</body>
</html>








