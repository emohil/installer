
// iframe自适应高度函数
function iframeHei(iframeId) {//iframeI iframe的id
  // var aa = window.frames["ifm"].document || document.getElementById("ifm").contentDocument;
  // $().contents().find()

  var ifm=document.getElementById(iframeId);
  var timer = setTimeout(function(){
    var sHeight = ifm.contentWindow.document.documentElement.scrollHeight;
    var oHeight = ifm.contentWindow.document.body.offsetHeight;
    var height = Math.min(sHeight, oHeight);
    ifm.height=height+40;
    clearTimeout(timer);
  },100);
}



$(document).ready(function() {

  $('#section > div > .list').click(function() {
    if( !$(this).hasClass('active') ) {
        $('#section > div > .list').removeClass('active');
        $(this).addClass('active');
    }
  });


});



window.onload=function(){


  var ifm=document.getElementById("ifm");   
  ifm.height=ifm.contentWindow.document.documentElement.scrollHeight+40;

  //当iframe中页面进行刷新时
  ifm.onload = ifm.onreadystatechange = function() {
        
       if (this.readyState && this.readyState != 'complete') {
          return;

       }else {
        // 若点击iframe中任意元素会产生变化时 重新定义iframe的高
        // $("#ifm").contents().find('.ss').click(function(){
          
        //     iframeHei('ifm');

        // });

          iframeHei('ifm');

       }
  }

    // 若点击iframe中任意元素会产生变化时 重新定义iframe的高
    // $("#ifm").contents().find('.ss').click(function(){

          // iframeHei('ifm');
    // });
}