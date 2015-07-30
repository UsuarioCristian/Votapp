'use strict';

// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('app.services', []).
value('version', '0.1')
.factory('ConsultoraFactory',['$http','ApiEndpointFactory', function($http, ApiEndpointFactory) {
	return{
		getConsultora:function(idConsultora){
			return $http.get(ApiEndpointFactory.ApiEndpoint +'/Votapp/services/consultoras/'+idConsultora)
		}
	}
	
}])

.factory('LoginFactory', ['$http',function($http) {
	return{
		
	}
}])
.factory('EleccionFactory', ['$http','ApiEndpointFactory',function($http, ApiEndpointFactory) {
	var elecciones = null;
    var promise = $http.get(ApiEndpointFactory.ApiEndpoint +'/Votapp/services/eleccion/getElecciones')
    .success(function (data) {
    	elecciones = data;
    });

    return {
      promise:promise,
      getEleccionesActuales: function () {
          return elecciones;//.getSomeData();
      },
      getEleccion:function(idEleccion){
    	  return $http.get(ApiEndpointFactory.ApiEndpoint +'/Votapp/services/eleccion/'+idEleccion)
      }
    }
}])

.factory('ApiEndpointFactory', ['$http','$location', function($http, $location) {
	
	var ApiEndpoint = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	
	return{
		ApiEndpoint : ApiEndpoint
	}	
	
}])