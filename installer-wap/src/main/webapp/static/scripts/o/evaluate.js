
$(document).ready(function() {
  
  $('.work_select .done div').click(function() {
    $('.work_select .done div').removeClass('selected');
    $(this).addClass('selected');
    var done = $(this).attr('data-done');
    var workdone = $('#wokedone').val(done);
    
    if (workdone != "") {
      $('form button').removeClass('disableClick');
      $('form button').removeAttr('disabled');
    } 
    
});
  
  

  var evaluateArr = ['极差','不满','一般','满意','惊喜'];

  $('.starText > .starVal > .d1 li').click(function(event) {
    var index = $(this).index();
    $(this).parent().children().each(function(i) {
      if( i<=index ) {
        $(this).find('img').attr('src','static/images/star1.png');
      }else {
        $(this).find('img').attr('src','static/images/star0.png');
      }
    });
    $(this).parent().next().html(evaluateArr[index]);
    $(this).parent().next().next().val(index+1);

  });


  $("#addPhoto").click(function() {
    $("#photoForm input").click();
  });


});

angular.module('app', ['ifu.form'])//
.controller('EvaluateController', ['$scope', '$http', '$window', function($scope, $http, $window) {

  $scope.data = {};
  
  $scope.doRemoveFile = function(item) {
    if (!$scope.file || !$scope.file.imgFile) {
      return false;
    }
    
    var files = $scope.file.imgFile;
    for (var i =0; i < files.length; i++) {
      if (files[i] == item) {
        files.splice(i, 1);
        break;
      }
    }
  }
  
  $scope.save = function() {
    
    if ($("#button1").html() == '正在提交,请稍后...') {
      return;
    }
    
    $scope.data.serveScore = $("#evaluate2").val();
    $scope.data.skillScore = $("#evaluate1").val();
    $scope.data.workdone = $('#wokedone').val();

    $("#button1").html('正在提交');
    $http({
      method : 'POST',
      url : 'indentEvaluate/evaluate.do', 
      headers : {
        'Content-Type' : undefined
      },
      transformRequest : function(data) {
        var formData = new FormData();

        formData.append("data", angular.toJson(data.data));

        for ( var f in data.file) {
          var items = data.file[f];
          for (var i in items) {
            formData.append(f, items[i]);
          }
        }
        return formData;
      },
      data : {
        data : $scope.data,
        file : $scope.file
      }
    }).then(function(response) {

      var data = response.data
      if (data.success) {
        $window.location.href="o/evaluateSuccess?indentId="+$scope.data.indentId;
      } else {
        $("#button1").html('提&nbsp;&nbsp;&nbsp;&nbsp;交');
        alert(data.msg);
      }
    });

  }

}]);
