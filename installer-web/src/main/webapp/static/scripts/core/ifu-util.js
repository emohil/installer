'use strict';

angular.module('ifu.util', ['ui.bootstrap', 'ifu.template'])//

.constant('ContextPath', function() {
  var baseTag = document.getElementsByTagName('base')[0];

  return baseTag.href;
}())

.factory('UrlUtil', ['ContextPath', '$uibModal', '$interval',// 
function(ContextPath, $uibModal, $interval) {

  return {
    transform : function(url) {
      return ContextPath + url;
    },

    autoJump : function(modalScope, fn) {
      modalScope.countdown = modalScope.countdown || 3;

      $uibModal.open({
        backdrop : 'static',
        templateUrl : 'template/modal/autoJump.html',
        scope : modalScope
      }).result.then(fn);

      var stop = $interval(function() {
        --modalScope.countdown;
        if (modalScope.countdown <= 0) {
          $interval.cancel(stop);
          fn();
        }
      }, 1000);
    }
  }
}]);
