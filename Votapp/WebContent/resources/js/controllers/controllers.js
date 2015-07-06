'use strict';

angular.module("app.controllers",[])
.controller("LoginController", ['$scope', 'LoginFactory', function($scope, LoginFactory){
	
}])
.controller("EleccionController", ['$scope', 'EleccionFactory', function($scope, EleccionFactory){
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
	
	$scope.showEleccion = function(eleccionId){
		console.log($location.path());
		$location.path();
	}
//	console.log('Promise is now resolved: '+EleccionFactory.getEleccionesActuales())
	$scope.elecciones = EleccionFactory.getEleccionesActuales();
	
	//$scope.elecciones = $scope.getEleccionesActuales();
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