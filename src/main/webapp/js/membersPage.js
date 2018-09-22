var membersPage = angular.module('membersPage', []);

membersPage.controller("membersPageController", function ($scope, $http) {


    $scope.description = "";
    $scope.affiliateLink = "";
    $scope.pathToImage = "";



    $scope.matchedBets = [];


    $scope.getAllMatchedBets = function () {
        $http.get("matched/get")
            .then(function(response){
               console.log(response);
               $scope.matchedBets = response.data;
            });
    };

    $scope.submitMatchedBet = function () {
        var matchedBetDto = {};
        matchedBetDto.description = $scope.description;
        matchedBetDto.affiliateLink = $scope.affiliateLink;
        matchedBetDto.pathToImage = $scope.pathToImage;

        var json = JSON.stringify(matchedBetDto);
        $http.post("matched/create", json, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
    }


});
