'use strict';

angular.module('ifu.form', [])//

.directive('positiveInteger', function() {
  return {
    require : 'ngModel',
    link : function(scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        if (text) {
          var transformedInput = text.replace(/[^0-9]/g, '');

          if (transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
          }
          return transformedInput;
        }
        return undefined;
      }
      ngModelCtrl.$parsers.push(fromUser);
    }
  };
})

.directive('fileModel2', ['$parse', function($parse) {
  return {
    require : 'ngModel',
    restrict : 'A',
    link : function(scope, element, attrs, ngModel) {

      element.bind('change', function(event) {
        var allFiles = ngModel.$viewValue || [];
        var files = event.target.files;
        for ( var f in files) {
          if (f == 'item' || f == 'length')
            continue;
          allFiles.push(files[f]);
        }
        scope.$apply(function() {
          ngModel.$setViewValue(allFiles);
        });
      });
    }
  };
}])

.directive('fngThumb', ['$window', function($window) {
  var helper = {
    support : !!($window.FileReader && $window.CanvasRenderingContext2D),
    isFile : function(item) {
      return angular.isObject(item) && item instanceof $window.File;
    },
    isImage : function(file) {
      var type = '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
      return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
    }
  };

  return {
    restrict : 'A',
    template : '<canvas/>',
    link : function(scope, element, attributes) {
      if (!helper.support)
        return;

      var params = scope.$eval(attributes.fngThumb);

      if (!helper.isFile(params.file))
        return;
      if (!helper.isImage(params.file))
        return;

      var canvas = element.find('canvas');
      var reader = new FileReader();

      reader.onload = onLoadFile;
      reader.readAsDataURL(params.file);

      function onLoadFile(event) {
        var img = new Image();
        img.onload = onLoadImage;
        img.src = event.target.result;
      }

      function onLoadImage() {
        var width = params.width || this.width / this.height * params.height;
        var height = params.height || this.height / this.width * params.width;
        canvas.attr({
          width : width,
          height : height
        });
        var cxt = canvas[0].getContext('2d');
        cxt.drawImage(this, 0, 0, width, height);

        // draw delete icon
        var iconWidth = 36, iconHeight = 36;
        var onLoadIcon = function() {
          cxt.drawImage(this, width - iconWidth, 0, iconWidth, iconHeight);
        }
        var icon = new Image();
        icon.onload = onLoadIcon;
        icon.src = 'static/images/delete.png';
      }
    }
  };
}])//
.directive('fngRemove', ['$parse', function($parse) {
  // 响应移除图片事件
  return {
    restrict : 'A',
    link : function(scope, element, attrs) {
      var handler = $parse(attrs.fngRemove);

      element.bind('click', function(event) {
        var target = event.target;
        var targetwidth = target.offsetWidth;
        var iconWidth = 36, iconHeight = 36;
        var x = event.pageX - target.offsetLeft;
        var y = event.pageY - target.offsetTop;

        if ((x < (targetwidth - iconWidth)) || (y > iconHeight)) {
          return;
        }
        scope.$apply(function() {
          handler(scope, {
            $event : event
          });
        });
      });
    }
  };
}]);