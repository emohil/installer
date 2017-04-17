angular.module('app', ['ifu.form']) //
.controller('FormController', ['$scope', '$http', function($scope, $http) {

  $scope.save = function() {

    $http({
      method : 'POST',
      url : "demo/formSave.do",
      // IMPORTANT!!! You might think this should be set to
      // 'multipart/form-data'
      // but this is not true because when we are sending up files the request
      // needs to include a 'boundary' parameter which identifies the boundary
      // name between parts in this multi-part request and setting the
      // Content-type
      // manually will not set this boundary parameter. For whatever reason,
      // setting the Content-type to 'undefined' will force the request to
      // automatically
      // populate the headers properly including the boundary parameter.
      headers : {
        'Content-Type' : undefined
      },
      // This method will allow us to change how the data is sent up to the
      // server
      // for which we'll need to encapsulate the model data in 'FormData'
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
      // Create an object that contains the model and files which will be
      // transformed
      // in the above transformRequest method
      data : {
        data : $scope.data,
        file : $scope.file
      }
    }).then(function() {
      console.log("success!");
    }, function() {
      console.log("failure!");
    });
  }
}]);