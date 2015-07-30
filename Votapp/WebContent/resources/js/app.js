'use strict';


// Declare app level module which depends on filters, and services
angular.module('app', [
  'ui.router',
  'app.services',
  'angular-storage',
  'app.controllers',
  'ui.bootstrap'
])
.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider ) {
	
	$urlRouterProvider.otherwise('/');	
	$stateProvider.state('eleccion', {url: '/eleccion',
		templateUrl: 'views/eleccion.html', controller: 'HomeController'})
	
	$stateProvider.state('home', {url:'/', templateUrl: 'views/home.html',  controller: 'EleccionController',
		    resolve:{
		        load:function(EleccionFactory){
		          // MyServiceData will also be injectable in your controller, if you don't want this you could create a new promise with the $q service
		          return EleccionFactory.promise;
		        }
		      }});
	//.state('nombreEstado', {camposExtras}) etc etc
}]);


