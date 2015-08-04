'use strict';

// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('app.services', []).
value('version', '0.1')


.factory('EleccionFactory', ['$http','ApiEndpointFactory','store',function($http, ApiEndpointFactory, store) {
	var elecciones = null;
    var promise = $http.get(ApiEndpointFactory.ApiEndpoint +'/Votapp/services/eleccion/getElecciones')
    .success(function (data) {
    	elecciones = data;
    	store.set('elecciones', data);
    });
    
    return {
     
      promise:promise,
      
      getEleccionesActuales: function () {
          return elecciones;//.getSomeData();
      },
      
    }
}])

.factory('ApiEndpointFactory', ['$http','$location', function($http, $location) {
	
	var ApiEndpoint = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	
	return{
		ApiEndpoint : ApiEndpoint
	}	
	
}])