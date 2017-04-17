<!DOCTYPE html>
<html lang="zh-cn">
  <head>
  <base href="${base}/">
    <meta charset="UTF-8">
    <!-- 响应式布局 -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, minimum-scale=1.0, user-scalable=0" />
    <title>评价众联工匠</title>
    <style>
      body,header,div,img {
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
        padding-bottom: 24px;
        background-color: #5890ff;
        color: #3B5999;
        font-size: 16px;
      }
      .left {
        float: left;
      }
      .right {
        float: right;
      }
      
      header {
        width: 92.5%;
        height: 40px;
        margin: 22px auto 18px;
      }
      .container {
        width: 92.5%;
        padding: 27px 0 31px;
        margin: 0 auto;
        border-radius: 3px;
        background-color: #fff;
        text-align: center;
      }
      .container .done2 {
        padding: 27px 0 24px;
        font-size: 18px;
      }
      .container .done4 {
        padding: 8px 0 22px;
      }
      .container .done5 {
        padding-top: 60px;
        font-size: 14px;
      }
      .container .done5 > div {
        line-height: 27px;
        text-align: center;
      }
      .container .done5 > .tel {
        color: #5890FF;
      }
      .container .done5 > .time {
        color: #9B9B9B;
      }
      

    </style>
  </head>
  <body>
  <input type="hidden" id="skillScore" value="${indentEvaluate.skillScore}"/>
  <input type="hidden" id="serveScore" value="${indentEvaluate.serveScore}"/>
    <header><img src="static/images/done_header.png" width="142"></header>
    <div class="container">
      <div class="done1"><img src="static/images/done_work.png" width="234" height="85"></div>
      <div class="done2">工匠得分</div>
      <div class="done3">安装技能</div>
      <div class="done4" id="skillScore1"><img height="30"></div>
      <div class="done3">服务态度</div>
      <div class="done4" id="serveScore1"><img height="30"></div>
      <div class="done5">
        <div class="tel">客服电话&nbsp;&nbsp;&nbsp;<span>4000000000</span></div>
        <div class="time">服务时间&nbsp;&nbsp;&nbsp;&nbsp;9:00 - 23:00</div>
      </div>
    </div>
  </body>
  <script src="static/js/jquery.min.js"></script>
  <script src="static/scripts/o/evaluateSuccess.js"></script>
</html>








