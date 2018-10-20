var membersPage = angular.module('membersPage', ['ui.sortable', 'ngFileUpload']);

membersPage.controller("membersPageController", function ($scope, $http) {


  $scope.description = "";
  $scope.affiliateLink = "";
  $scope.pathToImage = "";
  $scope.title = "";
  $scope.sequence = 0;
  $scope.matchedBets = [];
  $scope.errorMessage = "";
  $scope.imageSrc ="";
  $scope.linkLabel;



  $scope.image = {};
  $scope.onFileSelect = function($files) {
    console.log($files);

    $scope.image = $files[0];



  };


  function getUrl(bet) {
    return bet.affiliateLink;
  }

  $scope.deleteMatchedBet = function (id) {
    $http.delete("matched/delete/" + id);


    for (var i = $scope.matchedBets.length - 1; i >= 0; i--) {
      if ($scope.matchedBets[i].id === id) {
        $scope.matchedBets.splice(i, 1);
      }
    }

  };

  $scope.getAllMatchedBets = function () {
    $http.get("matched/get")
      .then(function (response) {
        console.log(response);
        $scope.matchedBets = response.data;
      });
  };

  $scope.submitMatchedBet = function () {
    var form = document.getElementById("create-matched-bet");
    $scope.errorMessage = " ";
    form.reset();


    var matchedBetDto = {};
    matchedBetDto.description = $scope.description;
    matchedBetDto.affiliateLink = $scope.affiliateLink;
    matchedBetDto.pathToImage = $scope.pathToImage;
    matchedBetDto.title = $scope.title;
    matchedBetDto.linkLabel = $scope.linkLabel;
    matchedBetDto.sequence = 0;
    var json = JSON.stringify(matchedBetDto);
    $http.post("matched/create", json, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(function response(response) {
      $scope.matchedBets.unshift(response.data);
      console.log($scope.matchedBets);
    },function errorCallback(response) {
      $scope.errorMessage = response.data.message;
    });

  }


  $scope.sortableOptions = {
    connectWith: ".col-md-12",

    stop: function (e, ui) {
      var json = JSON.stringify($scope.matchedBets);
      $http.post("matched/updateMatchedBets", json, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      });
    }
  };


});
