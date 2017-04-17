$(document).ready(function() {

  $('form .join .btn div').click(function() {
    $(this).addClass('displayNone').parent().next().removeClass('displayNone');
  });

  $('form input').bind('input', function() {
    var $input = $(this).val();
    var $reg = /^1\d{10}$/;

    if ($reg.test($input)) {
      $(this).parent().next().addClass('can_btn');
    } else {
      $(this).parent().next().removeClass('can_btn');
    }

  });
});

angular.module('app', [ 'ifu.form' ])//
.controller('invitePartnerController',
    [ '$scope', '$http', '$window', function($scope, $http, $window) {

      $scope.save = function() {
        $http.get('invitePartner/invite.do', {
          params : {
            accountId : $scope.data.accountId,
            mobile : $scope.data.mobile
          }
        }).then( function(response) {
          var data = response.data;
          if (data.success) {
            $window.location.href = 'invitePartner/share.do';
          }
        });
      }
    }]);
