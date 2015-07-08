'use strict';


// Declare app level module which depends on filters, and services
angular.module('app', [
  'ui.router',
  'app.services',
  'app.controllers',
  'ui.bootstrap'
])
.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider ) {
	
	$urlRouterProvider.otherwise('/');	
	$stateProvider.state('home', {url:'/', templateUrl: 'views/home.html',  controller: 'HomeController'})
	//.state('nombreEstado', {camposExtras}) etc etc
}]);


