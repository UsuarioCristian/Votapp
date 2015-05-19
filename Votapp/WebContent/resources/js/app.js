/*var app = angular.module("app", []) ;

app.controller('Main', ['$scope','$http', function($scope, $http){
	
	$scope.register = function(username, password){
		var user = {
				username: username,
				password: password
		}
		$http.post('http://localhost:8080/Votapp/services/usuario/crear', user)
		.success(function(data){
			$scope.username = data.username;
			$scope.password = data.password;				
		})
		.error(function(data){
			$scope.nombreConsultora = "Request failed";
			$scope.descConsultora = "";
		})
}
	
}])

*/

'use strict';


// Declare app level module which depends on filters, and services
angular.module('app', [
  'ngRoute',
  //'app.filters',
  'app.services',
  //'app.directives',
  'app.controllers'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {templateUrl: 'views/login.html', controller: 'LoginController'});
  $routeProvider.when('/consultora', {templateUrl: 'index2.html', controller: 'ConsultoraController'});
  $routeProvider.otherwise({redirectTo: '/', templateUrl:"views/home.html"});
}]);


