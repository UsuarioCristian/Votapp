'use strict';

angular.module("app.controllers",[])
.controller("LoginController", ['$scope', 'LoginFactory', function($scope, LoginFactory){
	
}])
.controller("EleccionController", ['$scope', 'EleccionFactory', function($scope, LoginFactory){
	$scope.getEleccion = function(eleccionId){
		EleccionFactory.getEleccion(eleccionId).then(
				function(response){
					$scope.consultora.nombre = response.data.nombre;
					$scope.consultora.descripcion = response.data.descripcion;
				},
				
				function(response){
					//error messagge
					console.log(response.data);
				}
		)
	}
}])

.controller('HomeController', ['$scope', function($scope){
		
	
}])