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

.directive('fileModel', ['$parse', function($parse) {
  return {
    restrict : 'A',
    link : function(scope, element, attrs) {
      var model = $parse(attrs.fileModel);
      var modelSetter = model.assign;

      element.bind('change', function(event) {
        var files = event.target.files;
        scope.$apply(function() {
          modelSetter(scope, files);
        });
      });
    }
  };
}])//
.directive('fngClick', function($parse) {
  return {
    compile : function($element, attr) {
      var handler = $parse(attr.fngClick);
      return function(scope, element, attr) {
        element.on('click', function(event) {
          scope.$apply(function() {
            var promise = handler(scope, {
              $event : event
            });
            if (promise && angular.isFunction(promise['finally'])) {
              element.attr('disabled', true);
              promise['finally'](function() {
                element.attr('disabled', false);
              });
            }
          });
        });
      };
    }
  };
})//
.directive('fngLink', function() {
  return {
    restrict: 'A',
    scope: {
      enabled: '=fngLink'
    },
    link: function(scope, element, attrs) {
      element.bind('click', function(event) {
        if(!scope.enabled) {
          event.preventDefault();
        }
      });
    }
  };
});
;