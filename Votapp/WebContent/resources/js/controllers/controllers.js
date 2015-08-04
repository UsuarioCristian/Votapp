'use strict';

angular.module("app.controllers",[])
.controller("LoginController", ['$scope', 'LoginFactory', function($scope, LoginFactory){
	
}])
.controller("EleccionController", ['$scope', '$state', 'EleccionFactory', 'store',  function($scope, $state, EleccionFactory, store){
	
//	$scope.showEleccion = function(eleccionId){
//		var encontre = false;
//		var i = 	0;
//		while(!encontre){
//			if($scope.elecciones[i].id==eleccionId){
//				encontre=true;
//				store.set("eleccionActual", $scope.elecciones[i]);
//			}
//			else {
//				i++;
//			}
//		
//		}
//		
//		$state.go("eleccion");
//	}
//	console.log('Promise is now resolved: '+EleccionFactory.getEleccionesActuales())
	$scope.elecciones = EleccionFactory.getEleccionesActuales();
	
	//$scope.elecciones = $scope.getEleccionesActuales();
}])

.controller('HomeController', ['$scope', 'EleccionFactory', '$stateParams', 'store', function($scope, EleccionFactory,$stateParams,store){

	$scope.fuente = { url : 'Deckdisc'};
	$scope.deptos = [];
	$scope.elecciones = store.get('elecciones');
	//Buscar la eleccion con el id que viene x url
	var encontre = false;
	var i = 0;
	while(!encontre && i < $scope.elecciones.length){
		if($scope.elecciones[i].id == $stateParams.eleccionId){
			encontre = true;
			$scope.eleccion = $scope.elecciones[i];
			$scope.tipoEleccion = $scope.eleccion.tipoEleccion;
			console.log("ESTE ES EL TIPO: "+$scope.tipoEleccion);
		}else{
			i++;
		}
	}
	
	$scope.deptos.push("Artigas")
	$scope.deptos.push("Cerro Largo")
	$scope.deptos.push("Durazno")
	$scope.deptos.push("Florida")
	$scope.deptos.push("Maldonado")
	$scope.deptos.push("Paysandu")
	$scope.deptos.push("Rivera")
	$scope.deptos.push("Salto")
	$scope.deptos.push("Soriano")
	$scope.deptos.push("Treinta y Tres")
	$scope.deptos.push("Canelones")
	$scope.deptos.push("Colonia")
	$scope.deptos.push("Flores")
	$scope.deptos.push("Lavalleja")
	$scope.deptos.push("Montevideo")
	$scope.deptos.push("Rio Negro")
	$scope.deptos.push("Rocha")
	$scope.deptos.push("San Jose")
	$scope.deptos.push("Tacuarembo")
	
}])
.controller("candidatoController", ['$scope', '$state', 'EleccionFactory', 'store', '$stateParams',  function($scope, $state, EleccionFactory, store, $stateParams){
	console.log("ACA ENTRO AL CONTROLLER");
	console.log($stateParams.eleccionId);
	$scope.fuente = { url : 'Deckdisc'};
	$scope.elecciones = store.get('elecciones');
	//Buscar la eleccion con el id que viene x url
	var encontre = false;
	var i = 0;
	while(!encontre && i < $scope.elecciones.length){
		if($scope.elecciones[i].id == $stateParams.eleccionId){
			encontre = true;
			$scope.eleccion = $scope.elecciones[i];
			console.log("TAMAÑO DE LA LISTA DE CANDIDATOS EN LA ELECCION: "+$scope.eleccion.dataCandidatos.length)

		}else{
			i++;
		}
	}
	
	var encontreCandi = false;
	var i = 0;

	while(!encontreCandi && i < $scope.eleccion.dataCandidatos.length){
		if($scope.eleccion.dataCandidatos[i].id == $stateParams.candidatoId){
			encontreCandi = true;
			$scope.candidato = $scope.eleccion.dataCandidatos[i];
			console.log("CANDIDATO : "+$scope.candidato.nombre)

		}else{
			i++;
		}
	}
	

}])


.controller("partidoController", ['$scope', '$state', 'EleccionFactory', 'store', '$stateParams',  function($scope, $state, EleccionFactory, store, $stateParams){
	console.log("ACA ENTRO AL CONTROLLER");
	console.log($stateParams.eleccionId);

	$scope.elecciones = store.get('elecciones');
	//Buscar la eleccion con el id que viene x url
	var encontre = false;
	var i = 0;
	while(!encontre && i < $scope.elecciones.length){
		if($scope.elecciones[i].id == $stateParams.eleccionId){
			encontre = true;
			$scope.eleccion = $scope.elecciones[i];
			console.log("TAMAÑO DE LA LISTA DE partidos EN LA ELECCION: "+$scope.eleccion.dataPartidos.length)

		}else{
			i++;
		}
	}
	
	var encontreParti = false;
	var i = 0;

	while(!encontreParti && i < $scope.eleccion.dataPartidos.length){
		if($scope.eleccion.dataPartidos[i].id == $stateParams.partidoId){
			encontreParti = true;
			$scope.partido = $scope.eleccion.dataPartidos[i];
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
.directive('youtubeVotapp', function () {
	
	function link(scope, element, attrs) {
		var url = scope.fuente.url;
		element.append('<iframe ng-transclude id="ytplayer" type="text/html" width="100%" height="100%" src="http://www.youtube.com/embed?listType=user_uploads&list='+url+'" frameborder="0"></iframe>');
	}
	
	return {
		scope: {
			fuente: '=fuente'
	    },
	    //transclude: true,
	    //templateUrl: 'views/youtube-plugin.html',
	    link: link
	};
})
.directive('votappStyle', function () {
	
	function link(scope, element, attrs) {
	}
	return {
		scope: {
	      eleccion: '=eleccion'
	    },
	    templateUrl: 'views/styles.html',
	    link: link
	};
})






