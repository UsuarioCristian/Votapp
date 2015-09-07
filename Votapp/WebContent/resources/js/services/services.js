'use strict';

// Demonstrate how to register services
// In this case it is a simple value service.
angular.module('app.services', []).
value('version', '0.1')


.factory('EleccionFactory', ['$http','ApiEndpointFactory','store','$q','$rootScope',function($http, ApiEndpointFactory, store,$q,$rootScope) {
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
      getEncuestasByIdEleccion : function(eleccionId){
    	  var deferred = $q.defer();
    	  $http.get(ApiEndpointFactory.ApiEndpoint +'/Votapp/services/encuesta/getEncuestasByEleccion/' + eleccionId)
    	  .success(function(data){
    		  deferred.resolve(data);
    		  //$rootScope.$apply();
    		 
    	  }).error(function(){
    		  
    	  })
    	  return deferred.promise;
      }
      
    }
}])

.factory('ApiEndpointFactory', ['$http','$location', function($http, $location) {
	
	var ApiEndpoint = $location.protocol() + "://" + $location.host() + ":" + $location.port();
	
	return{
		ApiEndpoint : ApiEndpoint
	}	
	
}])