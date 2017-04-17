<#include "/include/common.ftl" />
<link rel="stylesheet" href="static/css/photoswipe.css">
<link rel="stylesheet" href="static/css/default-skin/default-skin.css">
<title>评价众联工匠</title>
<style>
body, header, form, section, div, input, img, ul, li, textarea, button,
  footer, figure, a, figcaption {
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
  padding-bottom: 72px;
  background-color: #E9EAEE;
  color: #3E4454;
  font-size: 16px;
}

.left {
  float: left;
}

.right {
  float: right;
}

header {
  min-height: 111px;
  line-height: 24px;
  padding: 39px 27px 15px 126px;
  background: url('static/images/bkPol.png') 24px center no-repeat;
  background-size: 102px 81px;
  -webkit-background-size: 102px 81px;
  background-color: #F5A623;
  color: #FFF;
}

#photoShow {
  background-color: #fff;
  padding: 24px 0 9px 11px;
  overflow: hidden;
}

#photoShow>.d1 {
  padding: 18px 0 12px;
}

#photoShow>.d2 {
  overflow: hidden;
}

.photoShow {
  overflow: hidden;
}

.photoShow li {
  float: left;
  width: 78px;
  height: 73px;
  margin: 0 6px 8px 0;
  border: 1px solid #CACACA;
  border-radius: 2px;
  overflow: hidden;
}

form {
  margin-top: 10px;
}

.is_worked {
  margin: 10px 0;
  padding: 12px;
  background-color: #fff;
}

.is_worked>p {
  padding-bottom: 18px;
}

.work_select {
  margin-bottom: 6px;
  overflow: hidden;
  text-align: center;
}

.work_select .done {
  width: 45%;
}

.work_select .done0 {
  float: left;
}

.work_select .done1 {
  float: right;
}

.work_select>div div {
  width: 96%;
  height: 45px;
  margin: 0 auto;
  line-height: 43px;
  border: 1px solid #5890FF;
  border-radius: 4px;
}

.work_select>div div.selected {
  color: #fff;
  background: url('static/images/rightUp.png') top right no-repeat;
  background-size: 25%;
  background-color: #5890FF;
}

form section {
  background-color: #fff;
  padding: 12px 11px 13px;
}

form .starText {
  padding-bottom: 9px;
}

.starText>.d1 {
  padding-bottom: 9px;
  margin-right: -11px;
  margin-bottom: 9px;
  border-bottom: 1px solid #E9EAEE;
  font-weight: bold;
}

.starText>.starVal {
  overflow: hidden;
  padding-bottom: 18px;
  line-height: 39px;
}

.starText>.starVal>.d0 {
  margin-right: 9px;
  font-size: 14px;
}

.starText>.starVal>.d1 li {
  float: left;
  width: 36px;
  height: 36px;
  padding: 3px;
  margin-right: 4px;
}

.starText>.starVal>.d2 {
  color: #F5A623;
}

.starText>.d3 {
  padding: 12px 11px 20px 0;
  margin-right: -11px;
  border-bottom: 1px solid #E9EAEE;
}

.starText>.d3 textarea {
  width: 100%;
  line-height: 21px;
  padding: 7px 8px;
  border: 1px solid #CDCDCD;
  border-radius: 3px;
  background-color: #F6F6F6;
  font-size: 14px;
}

form .photo {
  margin-bottom: 18px;
  overflow: hidden;
}

.photo>.d1 {
  line-height: 20px;
  padding-bottom: 16px;
  font-weight: bold;
}

.photo>.d2 {
  overflow: hidden;
}

.photo>.takePhoto {
  position: relative;
  float: left;
  border: none;
}

.photo>.takePhoto input {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
}

button {
  width: 100%;
  height: 51px;
  line-height: 51px;
  background-color: #5890FF;
  color: #fff;
  text-align: center;
  font-size: 16px;
}
/*不可点击*/
.disableClick {
  background-color: #9B9B9B;
}

footer {
  padding-top: 15px;
  font-size: 14px;
}

footer>div {
  line-height: 27px;
  text-align: center;
}

footer>.d1 {
  color: #5890FF;
}

footer>.d2 {
  color: #9B9B9B;
}

/*消失与隐藏*/
.displayNone {
  display: none;
}

.my-gallery, .preview {
  /*width: 100%;*/
  float: left;
  overflow: hidden;
}

.my-gallery img {
  width: 78px;
  height: 73px;
}

.my-gallery figure, .preview figure {
  display: block;
  float: left;
  margin: 0 5px 5px 0;
  width: 78px;
  height: 73px;
}

.my-gallery figcaption {
  display: none;
}
</style>
</head>
<body ng-controller="EvaluateController">
  <header>以下是众联工匠完工的照片，请核查</header>
  <div id="photoShow"
    ng-init="data.indentId='${indentId}'; data.workerId='${workerId}'">
    <!-- photoswip 代码 -->
    <div class="my-gallery" itemscope>
      <#list indentNodeStepList as indentNodeStep> <#list
      indentNodeStep.fileIndexList as imgUrl>
      <figure itemprop="associatedMedia" itemscope>
        <a href="${imgUrl.fileUrl}" itemprop="contentUrl" data-size="1024x683">
          <img src="${imgUrl.thumbUrl}" itemprop="thumbnail"
          alt="Image description" />
        </a>
        <!-- 放大照片文字描述 -->
        <figcaption itemprop="caption description">${indentNodeStep.photoName}</figcaption>
      </figure>
      </#list> </#list>
    </div>
  </div>

  <form>
    <div class="is_worked">
      <p>工匠是否完成工作</p>
      <div class="work_select">
        <div class="done done0">
          <div data-done="0">未完成</div>
        </div>
        <div class="done done1">
          <div data-done="1">已完成</div>
        </div>
        <input type="hidden" id="wokedone" value="" />
      </div>
    </div>
    <section class="starText">
      <div class="d1">给工匠评分</div>
      <div class="d2 starVal">
        <div class="left d0">安装技能</div>
        <ul class="left d1">
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
        </ul>
        <div class="right d2">惊喜</div>
        <input type="hidden" ng-model="data.evaluate1" id="evaluate1"
          value="5" />
      </div>
      <div class="d4 starVal">
        <div class="left d0">服务态度</div>
        <ul class="left d1">
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
          <li><img src="static/images/star1.png" height="30"
            width="30"></li>
        </ul>
        <div class="right d2">惊喜</div>
        <input type="hidden" id="evaluate2" value="5" />
      </div>
      <div class="d3">
        <textarea ng-model="data.content" placeholder="输入您的意见或建议…"
          rows="4"></textarea>
      </div>
    </section>
    <section class="photo">
      <div class="d1">上传照片</div>
      <div class="preview">
        <figure ng-repeat="item in file.imgFile">
          <div fng-thumb="{file: item, width:78, height: 73}"
            fng-remove="doRemoveFile(item)"></div>
        </figure>
      </div>
      <div class="takePhoto">
        <img id="addPhoto" src="static/images/camera.png" height="73"
          width="78">
      </div>
    </section>
    <button id="button1" type="button" ng-click="save();"
      class="disableClick" disabled>提&nbsp;&nbsp;&nbsp;&nbsp;交</button>
  </form>
  <form id="photoForm" hidden>
    <input id="imgFile" type="file" accept="image/*" file-model2
      ng-model="file.imgFile" multiple />
  </form>
  <footer>
    <div class="d1">
      客服电话&nbsp;&nbsp;&nbsp;<span>4000000000</span>
    </div>
    <div class="d2">服务时间&nbsp;&nbsp;&nbsp;&nbsp;9:00 - 23:00</div>
  </footer>

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

  <script src="static/angular/angular.js"></script>
  <script src="static/scripts/core/ifu-form.js"></script>

  <script src="static/js/jquery.min.js"></script>
  <script src="static/photoswipe/photoswipe.min.js"></script>
  <script src="static/photoswipe/photoswipe-ui-default.min.js"></script>
  <script src="static/photoswipe/photoView.js"></script>
  <script src="static/scripts/o/evaluate.js"></script>

</body>
</html>
