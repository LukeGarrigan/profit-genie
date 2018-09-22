var membersPage = angular.module('membersPage', ['ui.sortable']);

membersPage.controller("membersPageController", function ($scope, $http) {


    $scope.description = "";
    $scope.affiliateLink = "";
    $scope.pathToImage = "";
    $scope.title = "";
    $scope.sequence = 0;


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
        matchedBetDto.title = $scope.title;
        matchedBetDto.sequence = 0;
        var json = JSON.stringify(matchedBetDto);
        $http.post("matched/create", json, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(function response(response){
            $scope.matchedBets.push(response.data);
            console.log($scope.matchedBets);
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
