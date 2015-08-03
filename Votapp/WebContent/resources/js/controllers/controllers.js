'use strict';

angular.module("app.controllers",[])
.controller("LoginController", ['$scope', 'LoginFactory', function($scope, LoginFactory){
	
}])
.controller("EleccionController", ['$scope', '$state', 'EleccionFactory', 'store',  function($scope, $state, EleccionFactory, store){
	
//	$scope.eleccion = {
//			nombre: null,
//			fecha: null,
//			descripcion: null,
//			imagen: null,
//			partidos: null,
//	}
//	

	
	$scope.showEleccion = function(eleccionId){
		var encontre = false;
		var i = 0;
		while(!encontre){
			if($scope.elecciones[i].id==eleccionId){
				encontre=true;
				store.set("eleccionActual", $scope.elecciones[i]);
			}
			else {
				i++;
			}
		
		}
		
		$state.go("eleccion");
	}
//	console.log('Promise is now resolved: '+EleccionFactory.getEleccionesActuales())
	$scope.elecciones = EleccionFactory.getEleccionesActuales();
	
	//$scope.elecciones = $scope.getEleccionesActuales();
}])

.controller('HomeController', ['$scope', 'EleccionFactory', '$stateParams', 'store', function($scope, EleccionFactory,$stateParams,store){
	
	$scope.fuente = { url : 'barackobama'};
	$scope.elecciones = store.get('elecciones');
	//Buscar la eleccion con el id que viene x url
	var encontre = false;
	var i = 0;
	while(!encontre && i < $scope.elecciones.length){
		if($scope.elecciones[i].id == $stateParams.eleccionId){
			encontre = true;
			$scope.eleccion = $scope.elecciones[i];
		}else{
			i++;
		}
	}
	
}])
.directive('fbVotappPlugin', function () {
	
	function link(scope, element, attrs) {
		
	}
	
	return {
		scope: {
	      fuente: '=fuente'
	    },
	    templateUrl: 'views/page-plugin.html',
	    link: link
	};
})












