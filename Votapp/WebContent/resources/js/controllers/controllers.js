'use strict';

angular.module("app.controllers",[])
.controller("LoginController", ['$scope', 'LoginFactory', function($scope, LoginFactory){
	
}])
.controller('ConsultoraController', ['$scope', 'ConsultoraFactory', function($scope, ConsultoraFactory) {
	
	$scope.updateResultado = function(consultoraId){
		
		ConsultoraFactory.getConsultora(consultoraId).then(
				function(response){
					$scope.consultora.nombre = response.data.nombre;
					$scope.consultora.descripcion = response.data.descripcion;
				},
				
				function(response){
					//error messagge
				}
		)
		
	};
	
	//$scope.updateResultado(1); //valor inicial de la consulta
	
}])