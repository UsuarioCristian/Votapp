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
	$stateProvider.state('eleccion', {
		url: '/eleccion/{eleccionId}',
		templateUrl: 'views/eleccion.html',
		controller: 'HomeController',
		resolve:{
	        load:function(EleccionFactory){	          
	          return EleccionFactory.promise;
	        }
	    }		
	})
	
	$stateProvider.state('home', {url:'/', templateUrl: 'views/home.html',  controller: 'EleccionController',
		    resolve:{
		        load:function(EleccionFactory){
		          // MyServiceData will also be injectable in your controller, if you don't want this you could create a new promise with the $q service
		          return EleccionFactory.promise;
		        }
		      }});
	//.state('nombreEstado', {camposExtras}) etc etc
	$stateProvider.state('candidato', {
		url:'/candidato/{eleccionId}/{candidatoId}', 
		templateUrl: 'views/candidato.html',
		controller: 'candidatoController'
		});
	
	$stateProvider.state('partido', {
		url:'/partido/{eleccionId}/{partidoId}', 
		templateUrl: 'views/partido.html',
		controller: 'partidoController'
		});
	
	$stateProvider.state('departamento', {
		url:'/departamento/{departamentoNombre}', 
		templateUrl: 'views/departamento.html', 
		});
}])

.run(['$window', 
  function($window) {

  $window.fbAsyncInit = function() {
    // Executed when the SDK is loaded

    FB.init({ 

      /* 
       The app id of the web app;
       To register a new app visit Facebook App Dashboard
       ( https://developers.facebook.com/apps/ ) 
      */

      appId: '1007712492573393', 

      /* 
       Adding a Channel File improves the performance 
       of the javascript SDK, by addressing issues 
       with cross-domain communication in certain browsers. 
      */

      channelUrl: 'app/index.html', 

      /* 
       Set if you want to check the authentication status
       at the start up of the app 
      */

      status: false, 

      /* 
       Enable cookies to allow the server to access 
       the session 
      */

      cookie: true, 

      /* Parse XFBML */

      xfbml: true 
    });

  };

  // Are you familiar to IIFE ( http://bit.ly/iifewdb ) ?

  (function(d){
    // load the Facebook javascript SDK 
    var js, 
    id = 'facebook-jssdk', 
    ref = d.getElementsByTagName('script')[0];

    if (d.getElementById(id)) {
      return;
    }

    js = d.createElement('script'); 
    js.id = id; 
    js.async = true;
    js.src = "//connect.facebook.net/en_US/all.js";

    ref.parentNode.insertBefore(js, ref);

  }(document));

}]);;


