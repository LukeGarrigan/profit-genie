var membersPage = angular.module('membersPage', ['ui.bootstrap','ngMaterial', 'ui.sortable']);

membersPage.controller("contactController", function ($scope, $http){

  $scope.email = "";
  $scope.firstName = "";
  $scope.secondName = "";
  $scope.subject = "";
  $scope.message = "";

  $scope.sendEmail = function () {

    var contactDto = {};

    contactDto.email = $scope.email;
    contactDto.firstName = $scope.firstName;
    contactDto.secondName = $scope.secondName;
    contactDto.subject = $scope.subject;
    contactDto.message= $scope.message;


    var json = JSON.stringify(contactDto);
    $http.post("user/email", json, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    });
  }


});

membersPage.controller("membersPageController", function ($scope, $http, $mdDialog, $filter) {


  $scope.description = "";
  $scope.affiliateLink = "";
  $scope.pathToImage = "";
  $scope.title = "";
  $scope.sequence = 0;
  $scope.matchedBets = [];
  $scope.errorMessage = "";
  $scope.imageSrc = "";
  $scope.linkLabel;
  $scope.itemsPerPage = 10;

  $scope.mdDisableBackdrop = true;

  $scope.curPage = 1;
  $scope.itemsPerPage = 7;
  $scope.maxSize = 5;



  $scope.image = {};




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

  $scope.getMatchedBetsAndUsers = function () {
    $scope.getAllMatchedBets();
    $scope.getAllUsers();
  };

  $scope.getAllMatchedBets = function () {
    $http.get("matched/get")
      .then(function (response) {
        console.log(response);
        $scope.matchedBets = response.data;

        for (var i = 0; i < $scope.matchedBets.length; i++) {
          $scope.matchedBets[i].date = $filter('date')($scope.matchedBets[i].date, "dd/MM/yyyy");
        }
        getCurrentPage();
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
    }, function errorCallback(response) {
      $scope.errorMessage = response.data.message;
    });

  };


  $scope.sortableOptions = {
    connectWith: ".col-md-12",

    stop: function (e, ui) {
      var stringifiedMatchedBets = [];
      for (var i = 0; i < $scope.matchedBets.length; i++) {

        var currentMatchedBet = $scope.matchedBets[i];

        var matchedBetDto = {};
        matchedBetDto.id = currentMatchedBet.id;
        matchedBetDto.description = currentMatchedBet.description;
        matchedBetDto.shortDescription = currentMatchedBet.shortDescription;
        matchedBetDto.affiliateLink = currentMatchedBet.affiliateLink;
        matchedBetDto.title = currentMatchedBet.title;
        matchedBetDto.showEntireDescription = currentMatchedBet.showEntireDescription;
        matchedBetDto.linkLabel = currentMatchedBet.linkLabel;


        stringifiedMatchedBets.push(matchedBetDto);
      }



      var json = JSON.stringify(stringifiedMatchedBets);
      $http.post("matched/updateMatchedBets", json, {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }).then(function response(response){
        getCurrentPage();
      });

    }
  };


  $scope.getAllUsers = function () {
    $http.get("admin/get-users")
      .then(function (response) {
        $scope.allUsers = response.data.users;
      });
  };


  $scope.changeMembershipStatus = function (key) {
    $http.post("admin/toggle", key, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(function response(response) {
      $scope.allUsers = response.data.users;
      alert("You have changed " + key + " membership status!");
    });


  }



  $scope.numOfPages = function () {
    return Math.ceil($scope.matchedBets.length / $scope.itemsPerPage);
  };

  $scope.$watch('curPage + numPerPage', function() {
    getCurrentPage();
  });

  function getCurrentPage(){
    var begin = (($scope.curPage - 1) * $scope.itemsPerPage);
    var end = begin + $scope.itemsPerPage;
    $scope.filteredItems = $scope.matchedBets.slice(begin, end);
  }


  $scope.displayEntireDescription = function(matchedBet) {

    if (matchedBet.description.length > 300) {
      matchedBet.showEntireDescription = !matchedBet.showEntireDescription;
    }
    console.log("Displaying entire description");
  }


  $scope.sendEmail = function(data) {

    console.log("test");
  }

});


membersPage.filter('paged', function() {
  return function(items, currentPage, itemsPerPage) {
    var begin = ((currentPage - 1) * itemsPerPage);
    var end = begin + itemsPerPage;
    return items.slice(begin, end);
  }


});



