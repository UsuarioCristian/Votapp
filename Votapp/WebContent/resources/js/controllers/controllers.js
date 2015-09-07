'use strict';

angular.module("app.controllers",[])
.controller("LoginController", ['$scope', 'LoginFactory', function($scope, LoginFactory){
	
}])
.controller("HomeController", ['$scope', '$state', 'EleccionFactory', 'store',  function($scope, $state, EleccionFactory, store){
	
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

.controller('EleccionController', ['$scope', 'EleccionFactory', '$stateParams', 'store', '$document', 'encuestas', '$timeout', function($scope, EleccionFactory,$stateParams,store, $document,encuestas,$timeout){

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
		}else{
			i++;
		}
	}
	$scope.deptos = $scope.eleccion.deptos;

	var anchorId = $stateParams.anchor;
	
	if(anchorId!=null){
		var someElement = angular.element(document.getElementById(anchorId));
	    $document.scrollToElementAnimated(someElement);
	}
	
	/***********************************************************************************/
	/*****************************SECCION GRAFICAS**************************************/
	/***********************************************************************************/
	
	$scope.encuestas = encuestas;
	
	/*El $timeout es para que se genere el id en la vista antes del renderTo de higcharts*/
	$timeout(function(){},500).then(
		function(){
			for (var index = 0; index < $scope.encuestas.length; index++) {
				var data = [];
				var resultado = $scope.encuestas[index].resultado;
				if($scope.encuestas[index].porCandidato){
					var mapCandidatos = resultado.mapCandidatos;
					var candidatos = $scope.encuestas[index].dataCandidatos;
					
					for(var i=0; i < candidatos.length; i++){
						var candidato = candidatos[i];
						var cantidad = mapCandidatos[candidato.id];
						var dato = {
				                name: candidato.nombre,
				                y: cantidad
				            }
						data.push(dato);
					}
				}else{
					var mapPartidos = resultado.mapPartidos;
					var partidos = $scope.encuestas[index].dataPartidos;
					
					for(var i = 0; i < partidos.length; i++){
						var partido = partidos[i];
						var cantidad = mapPartidos[partido.id];
						var dato = {
								name: partido.nombre,
								y: cantidad
							}
						data.push(dato);
					}
				}	
				
				var chartPie = new Highcharts.Chart({
				    chart: {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false,
				            type: 'pie',
				            renderTo: 'container-'+$scope.encuestas[index].id,
				        },
				        title: {
				            text: 'Resultado encuesta (prueba)'
				        },
				        subtitle: {
				        	text: 'Total encuestados: '+ $scope.encuestas[index].cantidadRespuestas,
				        },
				        tooltip: {
				            pointFormat: '{series.name}: <b>{point.y}</b>'
				        },
				        plotOptions: {
				            pie: {
				                allowPointSelect: true,
				                cursor: 'pointer',
				                dataLabels: {
				                    enabled: true,
				                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
				                    style: {
				                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				                    }
				                }
				            }
				        },
				        series: [{
				            name: "Total",
				            colorByPoint: true,
				            data : data
				        }]
				})
			}
		},
		function(){}		
	);
	
	
	
	
}])
.controller("candidatoController", ['$scope', '$state', 'EleccionFactory', 'store', '$stateParams',  function($scope, $state, EleccionFactory, store, $stateParams){
	
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
	
	var encontreCandi = false;
	var i = 0;

	while(!encontreCandi && i < $scope.eleccion.dataCandidatos.length){
		if($scope.eleccion.dataCandidatos[i].id == $stateParams.candidatoId){
			encontreCandi = true;
			$scope.candidato = $scope.eleccion.dataCandidatos[i];
			$scope.fuentes = $scope.candidato.dataFuenteDatos;

		}else{
			i++;
		}
	}
	
//	FB.XFBML.parse(document.getElementById('facebook-div'));
//	twttr.widgets.load(document.getElementById('twitter-div'));
	

}])


.controller("partidoController", ['$scope', '$state', 'EleccionFactory', 'store', '$stateParams',  function($scope, $state, EleccionFactory, store, $stateParams){
		
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
	
	var encontreParti = false;
	var i = 0;

	while(!encontreParti && i < $scope.eleccion.dataPartidos.length){
		if($scope.eleccion.dataPartidos[i].id == $stateParams.partidoId){
			encontreParti = true;
			$scope.partido = $scope.eleccion.dataPartidos[i];
			$scope.fuentes = $scope.partido.dataFuenteDatos;
		}else{
			i++;
		}
	}

}])
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
.directive('twitterVotapp', function () {
	
	function link(scope, element, attrs) {
		var url = scope.fuente.url;
//		'<iframe ng-transclude id="ytplayer" type="text/html" width="100%" height="100%" src="http://www.youtube.com/embed?listType=user_uploads&list='+url+'" frameborder="0"></iframe>');
		element.append('<a class="twitter-timeline" href="https://twitter.com/'+url+'" data-widget-id="628667478659452928">Tweets por el @'+url+'.</a>');
		twttr.widgets.load();
	}
	
	return {
		scope: {
			fuente: '=fuente'
	    },
	    //transclude: true,
	    //templateUrl: 'views/twitter-plugin.html',
	    link: link
	};
})
.directive('facebookVotapp', function () {
	
	function link(scope, element, attrs) {
		var url = scope.fuente.url;
		element.append('<div class="fb-page" data-href="https://www.facebook.com/'+url+'" data-small-header="false" data-adapt-container-width="true" data-hide-cover="false" data-show-facepile="true" data-show-posts="true"><div class="fb-xfbml-parse-ignore"><blockquote cite="https://www.facebook.com/'+url+'"><a ng-href="https://www.facebook.com/'+url+'">Facebook</a></blockquote></div></div>');
		FB.XFBML.parse();
	}
	return {
		scope: {
	      fuente: '=fuente'
	    },
//	    templateUrl: 'views/page-plugin.html',
	    link: link
	};
})

.controller("deptoController", ['$scope', '$state', 'EleccionFactory', 'store', '$stateParams',  function($scope, $state, EleccionFactory, store, $stateParams){
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
	encontre = false;
	i = 0;
	while (!encontre && i<$scope.eleccion.deptos.length){
		if($scope.eleccion.deptos[i].id == $stateParams.departamentoId){
			encontre = true;
			$scope.deptoSel = $scope.eleccion.deptos[i];
		}
		else{
			i++;
		}
	}
	
	
	$scope.candidatosXDepto = [];
	
	for (var x=0;x<$scope.eleccion.dataCandidatos.length;x++){
		if($scope.eleccion.dataCandidatos[x].idDepto == $stateParams.departamentoId){
			$scope.candidatosXDepto.push($scope.eleccion.dataCandidatos[x]);

		}
	
	}
	
	$scope.idPartidosXDepto = $scope.deptoSel.coleccionIdPartidos;
	$scope.partidosXDeptos = [];	
	for(var x=0;x<$scope.idPartidosXDepto.length;x++){
		console.log("Length: "+$scope.idPartidosXDepto.length);
		var encontre = false;
		var i = 0;
		while(!encontre && i<$scope.eleccion.dataPartidos.length){
			console.log("Length 19 "+$scope.eleccion.dataPartidos.length);
			if($scope.idPartidosXDepto[x] == $scope.eleccion.dataPartidos[i].id){
				encontre = true;
				$scope.partidosXDeptos.push($scope.eleccion.dataPartidos[i]);
				console.log("encontre un partido");
			}
			else{
				i++;
			}
			
		}
				
	}
	
	
	


	

}])



