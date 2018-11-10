var membersPage = angular.module('membersPage', ['ngMaterial', 'ui.sortable']);

membersPage.controller("membersPageController", function ($scope, $http, $mdDialog, $filter) {


  $scope.description = "";
  $scope.affiliateLink = "";
  $scope.pathToImage = "";
  $scope.title = "";
  $scope.sequence = 0;
  $scope.matchedBets = [];
  $scope.errorMessage = "";
  $scope.imageSrc ="";
  $scope.linkLabel;

  $scope.mdDisableBackdrop = true;



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

  $scope.getMatchedBetsAndUsers = function() {
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

  };


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


  $scope.getAllUsers = function () {
    $http.get("admin/get-users")
      .then(function (response) {
        $scope.allUsers = response.data.users;
      });
  };


  $scope.changeMembershipStatus = function(key) {
    $http.post("admin/toggle", key, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(function response(response) {
      $scope.allUsers = response.data.users;
      alert("You have changed " + key +" membership status!");
    });


  }


  $scope.editTask = function (ev, task) {
    // Appending dialog to document.body to cover sidenav in docs app
    var confirm = $mdDialog.prompt()
      .title('Edit your task!')
      .textContent('Be sure to keep it short and sweet!')
      .placeholder('Task')
      .ariaLabel('New Task')
      .initialValue(task.description)
      .targetEvent(ev)
      .clickOutsideToClose(true)
      .ok('Submit')
      .cancel('Cancel');
    $mdDialog.show(confirm).then(function (result) {

      var temp = task.description;
      task.description = result;
      // if ($scope.isValidTask(task, ev)) {
      //   $scope.addTask(task, task.status, ev);
      // } else {
      //   setTimeout(function () {
      //     $scope.editTask(ev, task);
      //   }, 2000);
      // }
      task.message = temp;

    });
  };

  $scope.showPrompt = function (ev, status) {
    // Appending dialog to document.body to cover sidenav in docs app
    var confirm = $mdDialog.prompt()
      .title('Create a new task!')
      .textContent('Be sure to keep it short and sweet!')
      .placeholder('Task')
      .ariaLabel('New Task')
      .initialValue('')
      .targetEvent(ev)
      .clickOutsideToClose(true)
      .ok('Add!')
      .cancel('Cancel');

    $mdDialog.show(confirm).then(function (result) {
      // task = {};
      // task.message = result;
      // if ($scope.isValidTask(task, ev)) {
      //   $scope.addTask(task, status, ev);
      // }

    }, function () {
      $scope.status = 'You didn\'t name your dog.';
    });
  };


});
