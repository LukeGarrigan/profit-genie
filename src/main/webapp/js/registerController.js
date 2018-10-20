app.controller("registerController", function ($scope, $http, $window) {


    $scope.user = {};
    $scope.password = "";
    $scope.email = "";
    $scope.isAuthorised = false;
    $scope.loginErrorMessage = "";
    $scope.registerErrorMessage = "";
    $scope.forgotPasswordMessage = "";

    $scope.forgotPassword = function() {
      $http.post("user/resetPassword", $scope.email, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      });

      $scope.forgotPasswordMessage = "Email sent to " + $scope.email
    };

    $scope.registerUser = function () {
        if (!passwordsMatch()) {
            $scope.registerErrorMessage = "Passwords do not match";
        } else {
            registerNewUser();
        }
    };

    var passwordsMatch = function () {
        return $scope.password === $scope.confirmPassword;
    };

    var registerNewUser = function () {
        console.log("in the registering bit");
        $scope.user.password = $scope.password;
        $scope.user.confirmPassword = $scope.confirmPassword;
        $scope.user.email = $scope.email;

        var userAsJson = JSON.stringify($scope.user);
        $http.post("user/create", userAsJson, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function mySuccess(response) {
            $scope.$parent.userId = response.data.id;
            $scope.isAuthorised = true;
            $window.location.href = '/members-page.html';
        }, function myFailure(response) {
            $scope.registerErrorMessage = response.data.message;
        });

    };

    $scope.updatePassword = function() {
      if (!passwordsMatch()) {
        $scope.registerErrorMessage = "Passwords do not match";
      } else {
        $http.post("user/savePassword", $scope.password, {
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
          }
        }).then(function mySuccess(response){
          $window.location.href = '/members-page.html';
        }, function errorCallback(response) {
          $scope.loginErrorMessage = response.data.message;
        });
      }
    }


    $scope.loginUser = function () {

        $http({
            method: 'POST',
            url: '/api/authentication',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            data: 'username='+$scope.email+'&password='+$scope.password
        }).then(function mySuccess(response) {
          $window.location.href = '/members-page.html';
        }, function errorCallback(response) {
          $scope.loginErrorMessage = "The credentials entered are incorrect";
        });

    };


});