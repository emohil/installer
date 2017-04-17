<#include "/include/common.ftl" />
<link rel="stylesheet" href="static/css/common.css" type="text/css" />
<link rel="stylesheet" href="static/css/index.css" type="text/css" />
<title>众联工匠管理系统</title>
</head>
<body>
  <div class="bkI">
    <img src="static/images/indexBgI.jpg" width="100%" height="100%">
  </div>
  <div id="indexForm">
    <form class="form" action="login.do" method="post">
      <div class="logo">
        <img src="static/images/indexLogin.png" height="81" width="80">
      </div>
      <div class="inputDiv">
        <div class="input">
          <input type="text" id="user" name="data.user" value="${data.user}">
          <div id="userTip" class="errMsg hide">用户名不能为空</div>
        </div>
        <div class="placeholder">用户名</div>
      </div>
      <div class="inputDiv">
        <div class="input">
          <input type="password" id="pwd" name="data.pwd" value="${data.pwd}">
          <div id="pwdTip" class="errMsg hide">密码不能为空</div>
        </div>
        <div class="placeholder">密&nbsp;&nbsp;&nbsp;码</div>
      </div>
      <button type="submit" class="login">登&nbsp;&nbsp;&nbsp;录</button>
      <div class="inputDiv errMsg">${err_msg}</div>
    </form>
  </div>
  
  <script src="static/js/jquery-1.11.1.min.js"></script>
  <script src="static/scripts/index.js"></script>

</body>
</html>