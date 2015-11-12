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
	
	var i=0;
	while(i < $scope.elecciones.length){
		var len=$scope.elecciones[i].descripcion.length;
	    if(len>200){
	    	$scope.elecciones[i].descripcionResumida = $scope.elecciones[i].descripcion.substr(0,200)+'...';
	    } else {
	    	$scope.elecciones[i].descripcionResumida = $scope.elecciones[i].descripcion;
	    }
		i++;
	}
	
	//$scope.elecciones = $scope.getEleccionesActuales();
}])

.controller('EleccionController', ['$scope', 'EleccionFactory', '$stateParams', 'store', '$document', 'encuestas', '$timeout', '$state',function($scope, EleccionFactory,$stateParams,store, $document,encuestas,$timeout,$state){

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
			$scope.noticias = $scope.eleccion.dataNoticias;
			console.log("NOMBRE DEL CANDIDATO: "+$scope.eleccion.dataCandidatos[0].imagen);
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
	
	$scope.updateDpto = function (deptoSelected) {
		
		$scope.dpto = deptoSelected;
	};
	
	/***********************************************************************************/
	/*****************************SECCION PAGINACION************************************/
	/***********************************************************************************/
	
	$scope.viewby = 6;
	$scope.totalItems = $scope.eleccion.dataCandidatos.length;
	$scope.currentPage = 1;
	$scope.itemsPerPage = $scope.viewby;
	$scope.maxSize = 5; //Number of pager buttons to show
	
	$scope.setPage = function (pageNo) {
	  $scope.currentPage = pageNo;
	};
	
	$scope.pageChanged = function() {
//	  console.log('Page changed to: ' + $scope.currentPage);
	};

	$scope.setItemsPerPage = function(num) {
	  $scope.itemsPerPage = num;
	  $scope.currentPage = 1; //reset to first paghe
	}
	
	$scope.totalItemsPartidos = $scope.eleccion.dataCandidatos.length;
	$scope.currentPagePartidos = 1;
	$scope.itemsPerPagePartidos = $scope.viewby; 
	$scope.pageChangedPartidos = function() {
//		  console.log('Page changed to: ' + $scope.currentPage);
		};
	
	/***********************************************************************************/
	/*****************************SECCION CAROUSEL**************************************/
	/***********************************************************************************/
	
	$scope.myInterval = 5000;
	
	
	/***********************************************************************************/
	/*****************************SECCION GRAFICAS**************************************/
	/***********************************************************************************/
	
	$scope.encuestas = encuestas;
	$scope.graficas= [];
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
				            text: 'Resultado encuesta: '+$scope.encuestas[index].nombre
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
				
				$scope.graficas[index] = chartPie;
			}
		},
		function(){
			/*Error del timeout*/
			console.log('Error $timeout')
		}		
	);
	
	$scope.goToEncuesta = function(encuesta, eleccion){
		$state.go('encuesta', {encuesta : encuesta, eleccionId : eleccion});
	}
	
	
	$scope.onSlideChanged = function (nextSlide, direction) {
	    console.log('onSlideChanged:', direction, nextSlide);
	    $timeout(function(){
	    for (var index = 0; index < $scope.graficas.length; index++) {
	    	
	    			$scope.graficas[index].reflow();	
	    		}
		},0);
	};
	
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
		element.append('<a class="twitter-timeline" data-widget-id="'+url+'"></a>');
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
		console.log("Esto essssssssssssss: "+$scope.eleccion.dataCandidatos[x].idDepto);
		if($scope.eleccion.dataCandidatos[x].idDepto == $stateParams.departamentoId){
			$scope.candidatosXDepto.push($scope.eleccion.dataCandidatos[x]);
			console.log("ALGO: "+$scope.candidatosXDepto.length);
		}
	
	}
	
	$scope.idPartidosXDepto = $scope.deptoSel.coleccionIdPartidos;
	$scope.partidosXDeptos = [];	
	for(var x=0;x<$scope.idPartidosXDepto.length;x++){
		var encontre = false;
		var i = 0;
		while(!encontre && i<$scope.eleccion.dataPartidos.length){
			if($scope.idPartidosXDepto[x] == $scope.eleccion.dataPartidos[i].id){
				encontre = true;
				$scope.partidosXDeptos.push($scope.eleccion.dataPartidos[i]);
			}
			else{
				i++;
			}
			
		}
				
	}
	

	/***********************************************************************************/
	/*****************************SECCION PAGINACION************************************/
	/***********************************************************************************/
	
	$scope.viewby = 6;
	$scope.totalItems = $scope.eleccion.dataCandidatos.length;
	$scope.currentPage = 1;
	$scope.itemsPerPage = $scope.viewby;
	$scope.maxSize = 5; //Number of pager buttons to show
	
	$scope.setPage = function (pageNo) {
	  $scope.currentPage = pageNo;
	};
	
	$scope.pageChanged = function() {
//	  console.log('Page changed to: ' + $scope.currentPage);
	};

	$scope.setItemsPerPage = function(num) {
	  $scope.itemsPerPage = num;
	  $scope.currentPage = 1; //reset to first paghe
	}
	
	$scope.totalItemsPartidos = $scope.eleccion.dataCandidatos.length;
	$scope.currentPagePartidos = 1;
	$scope.itemsPerPagePartidos = $scope.viewby; 
	$scope.pageChangedPartidos = function() {
//		  console.log('Page changed to: ' + $scope.currentPage);
		};

}])

.controller("partidoXDepto", ['$scope', '$state', 'EleccionFactory', 'store', '$stateParams',  function($scope, $state, EleccionFactory, store, $stateParams){
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
	var encontre = false;
	var i = 0;
	while(!encontre && i<$scope.eleccion.dataPartidos.length){
		if($stateParams.partidoId == $scope.eleccion.dataPartidos[i].id){
			encontre = true;
			$scope.partido = $scope.eleccion.dataPartidos[i];
		}else
			i++;
	}
	
	var encontre = false;
	var i = 0;
	while(!encontre && i<$scope.eleccion.deptos.length){
		if($scope.eleccion.deptos[i].id == $stateParams.departamentoId){
			encontre = true;
			$scope.departamento = $scope.eleccion.deptos[i];
		}else
			i++;
	}
	
	$scope.fuentesNoticias = [];
	
	for(var x=0;x<$scope.partido.dataFuenteDatos.length;x++){
		for(var y=0;y<$scope.departamento.listaFuenteDatos.length;y++){
				if ($scope.partido.dataFuenteDatos[x].id == $scope.departamento.listaFuenteDatos[y].id){
					$scope.fuentesNoticias.push($scope.departamento.listaFuenteDatos[y]);
					
				}
		}
	}
	
	console.log($scope.partido.dataFuenteDatos.length);
	console.log($scope.departamento.listaFuenteDatos.length);
}])
.controller('encuestaController', ['$scope', '$stateParams', '$timeout',function($scope,$stateParams,$timeout){
	
	$scope.encuesta = $stateParams.encuesta;
	$scope.eleccion = {};
	$scope.eleccion.id = $stateParams.eleccionId;
	
	/*******************************/
	/*******Seccion Graficas********/
	/*******************************/
	
	$scope.tiposGraficas = [];
	var graficaPie = {
			nombre : 'Gráfica de Pie',
			id : 1
	}
	$scope.tiposGraficas.push(graficaPie);
	
	if($scope.encuesta.preguntarEdad){
		var graficaEdad = {
				nombre : 'Gráfica columna segun edad',
				id : 2
		}
		$scope.tiposGraficas.push(graficaEdad);
	}
	
	if($scope.encuesta.preguntarNivelEstudio){
		var graficaEducacion = {
				nombre : 'Gráfica columna segun nivel educativo',
				id : 3
		}
		$scope.tiposGraficas.push(graficaEducacion);
	}
	
	if($scope.encuesta.preguntarLista){
		var graficaLista = {
				nombre : 'Gráfica listas',
				id : 4
		}
		$scope.tiposGraficas.push(graficaLista);
	}
	
	if($scope.encuesta.preguntarSiTrabaja){
		var graficaTrabaja = {
				nombre : 'Gráfica segun actividad laboral',
				id : 5
		}
		$scope.tiposGraficas.push(graficaTrabaja);
	}
	
	if($scope.encuesta.preguntarIngresos){
		var graficaIngresos = {
				nombre : 'Gráfica segun rango ingresos',
				id : 6
		}
		$scope.tiposGraficas.push(graficaIngresos);
	}
	
	if($scope.encuesta.preguntarSexo){
		var graficaGenero = {
				nombre : 'Gráfica segun género',
				id : 7
		}
		$scope.tiposGraficas.push(graficaGenero);
	}
	
	$scope.graficaSeleccionada = $scope.tiposGraficas[0];
		
	/*********************************************************************/
	/********************CARGA DE DATOS DEL PIE CHART*********************/
	/*********************************************************************/
	/*********************************************************************/
	var data = [];
	var resultado = $scope.encuesta.resultado;
	if($scope.encuesta.porCandidato){
		var mapCandidatos = resultado.mapCandidatos;
		var candidatos = $scope.encuesta.dataCandidatos;
		
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
		var partidos = $scope.encuesta.dataPartidos;
		
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
	
	/*****Como chartPie es la grafica que aparece por defecto, 
	 * entonces se debe iniciar aqui (las demas lo hacen con el ng-change $scope.changeChart)**********/
	
	$timeout(function(){},1000).then(
			function(){
				var chartPie = new Highcharts.Chart({
				    chart: {
				            plotBackgroundColor: null,
				            plotBorderWidth: null,
				            plotShadow: false,
				            type: 'pie',
				            renderTo: 'container',
				        },
				        title: {
				            text: 'Resultado encuesta (prueba)'
				        },
				        subtitle: {
				        	text: 'Total encuestados: '+ $scope.encuesta.cantidadRespuestas,
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
			},
			function(){}		
	);
	
	
	
	/****************************************************************************/
	/********************CARGA DE DATOS DEL GRAFICA POR EDAD*********************/
	/****************************************************************************/
	/****************************************************************************/
	if($scope.encuesta.preguntarEdad){
		var serieEdad = [];
		var de18a23 = resultado.mapEdad18a23;
		var de24a30 = resultado.mapEdad24a30;
		var de31a50 = resultado.mapEdad31a50;
		var de51omas = resultado.mapEdad51omas;
		
		var valorVotoBlanco = -1;
		var valorVotoNoSabe = -1;
		
		if($scope.encuesta.porCandidato){
			var mapCandidatos = resultado.mapCandidatos;
			var candidatos = $scope.encuesta.dataCandidatos;		

			for(var i=0; i < candidatos.length; i++){
				var candidato = candidatos[i];
				var cantidad18a23 = de18a23[candidato.id];
				var cantidad24a30 = de24a30[candidato.id];
				var cantidad31a50 = de31a50[candidato.id];
				var cantidadde51omas = de51omas[candidato.id];
				
				var valor = {
		                name: candidato.nombre,
		                data: [cantidad18a23, cantidad24a30, cantidad31a50, cantidadde51omas]
		            }
				if(candidato.id === 0)
					valorVotoBlanco = valor;
				else if(candidato.id === -1)
					valorVotoNoSabe = valor;
				else
					serieEdad.push(valor);
			}
		}else{
			var mapPartidos = resultado.mapPartidos;
			var partidos = $scope.encuesta.dataPartidos;
			
			for( var i = 0; i < partidos.length; i++){
				var partido = partidos[i];
				var cantidad18a23 = de18a23[partido.id];
				var cantidad24a30 = de24a30[partido.id];
				var cantidad31a50 = de31a50[partido.id];
				var cantidadde51omas = de51omas[partido.id];
				
				var valor = {
		                name: partido.nombre,
		                data: [cantidad18a23, cantidad24a30, cantidad31a50, cantidadde51omas]
		            }
				
				if(partido.id === 0)
					valorVotoBlanco = valor;
				else if(partido.id === -1)
					valorVotoNoSabe = valor;
				else
					serieEdad.push(valor);
			}
		}	
		if(valorVotoBlanco !== -1) /*Cuidado, hay que ver q existan votos en blanco*/
			serieEdad.push(valorVotoBlanco);
		if(valorVotoNoSabe !== -1) /*Cuidado, hay que ver q existan respuestas "no sabe"*/
			serieEdad.push(valorVotoNoSabe);
	}
	
	/****************************************************************************/
	/****************CARGA DE DATOS DE GRAFICA POR NIVEL ESTUDIO*****************/
	/****************************************************************************/
	/****************************************************************************/
	if($scope.encuesta.preguntarNivelEstudio){
		var serieEstudio = [];
		var primaria = resultado.mapNivelEstudioPrimaria;
		var secundaria = resultado.mapNivelEstudioSecundaria;
		var terciario = resultado.mapNivelEstudioTerciario;
		var noSabe = resultado.mapNivelEstudioNoSabe;
		
		var valorVotoBlanco = -1;
		var valorVotoNoSabe = -1;
		
		if($scope.encuesta.porCandidato){
			var mapCandidatos = resultado.mapCandidatos;
			var candidatos = $scope.encuesta.dataCandidatos;		
			
			for(var i=0; i < candidatos.length; i++){
				var candidato = candidatos[i];
				var cantidadPrimaria = primaria[candidato.id];
				var cantidadSecundaria = secundaria[candidato.id];
				var cantidadTerciario = terciario[candidato.id];
				var cantidadNoSabe = noSabe[candidato.id];
				
				var valor = {
		                name: candidato.nombre,
		                data: [cantidadPrimaria, cantidadSecundaria, cantidadTerciario, cantidadNoSabe]
		            }
				if(candidato.id === 0)
					valorVotoBlanco = valor;
				else if(candidato.id === -1)
					valorVotoNoSabe = valor;
				else
					serieEstudio.push(valor);
			}
		}else{
			var mapPartidos = resultado.mapPartidos;
			var partidos = $scope.encuesta.dataPartidos;
			
			for( var i = 0; i < partidos.length; i++){
				var partido = partidos[i];
				var cantidadPrimaria = primaria[partido.id];
				var cantidadSecundaria = secundaria[partido.id];
				var cantidadTerciario = terciario[partido.id];
				var cantidadNoSabe = noSabe[partido.id];
				
				var valor = {
		                name: partido.nombre,
		                data: [cantidadPrimaria, cantidadSecundaria, cantidadTerciario, cantidadNoSabe]
		            }
				if(partido.id === 0)
					valorVotoBlanco = valor;
				else if(partido.id === -1)
					valorVotoNoSabe = valor;
				else
					serieEstudio.push(valor);
			}
		}
		
		if(valorVotoBlanco !== -1) /*Cuidado, hay que ver q existan votos en blanco*/
			serieEstudio.push(valorVotoBlanco);
		if(valorVotoNoSabe !== -1) /*Cuidado, hay que ver q existan respuestas "no sabe"*/
			serieEstudio.push(valorVotoNoSabe);		
	}
	/****************************************************************************/
	/****************************************************************************/
	/****************CARGA DE DATOS DE GRAFICA POR LISTA*****************/
	/****************************************************************************/
	/****************************************************************************/
	if($scope.encuesta.preguntarLista){
		var serieLista = [{
			name: "Votos",
		    colorByPoint: true,
		    data: []
		}];
		
		var drilldownLista = {
				series:[]
		}
		
		var mapListas = resultado.mapListas;
		if($scope.encuesta.porCandidato){
			var mapCandidatos = resultado.mapCandidatos;
			var candidatos = $scope.encuesta.dataCandidatos;		
			
			for(var i=0; i < candidatos.length; i++){				
				var candidato = candidatos[i];
				var serie = {
						name: candidato.nombre,
						id: candidato.id,
						data:[]						
				}
				
				for(var j = 0; j < candidato.dataListas.length; j++){
					var dato = ['Lista '+candidato.dataListas[j].numero, mapListas[candidato.dataListas[j].id]];
					serie.data.push(dato);
				}		
					
				drilldownLista.series.push(serie);				
				
				var valor = {
		                name: candidato.nombre,
		                y : mapCandidatos[candidato.id],
		                drilldown: candidato.id,
		            }
				serieLista[0].data.push(valor);
			}
		}else{
			var mapPartidos = resultado.mapPartidos;
			var partidos = $scope.encuesta.dataPartidos;
			
			for(var i=0; i < partidos.length; i++){				
				var partido = partidos[i];
				var serie = {
						name: partido.nombre,
						id: partido.id,
						data:[]						
				}
				
				for(var j = 0; j < partido.listas.length; j++){
					var dato = ['Lista '+partido.listas[j].numero, mapListas[partido.listas[j].id]]
					serie.data.push(dato);
				}		
					
				drilldownLista.series.push(serie);				
				
				var valor = {
		                name: partido.nombre,
		                y : mapPartidos[partido.id],
		                drilldown: partido.id,
		            }
				serieLista[0].data.push(valor);
			}
		}		
		
	}
	
	/****************************************************************************/
	/****************************************************************************/
	/**********************CARGA DE DATOS DE GRAFICA TRABAJO*********************/
	/****************************************************************************/
	/****************************************************************************/
	
	if($scope.encuesta.preguntarSiTrabaja){
		var serieTrabaja = [];
		var trabaja = resultado.mapTrabaja;
		var noTrabaja = resultado.mapNoTrabaja;
		
		if($scope.encuesta.porCandidato){
			var candidatos = $scope.encuesta.dataCandidatos;
			for (var i = 0; i < candidatos.length; i++) {
				var candidato = candidatos[i];
				var cantidadTrabaja = trabaja[candidato.id];
				var cantidadNoTrabaja = noTrabaja[candidato.id];
				
				var valor = {
						name : candidato.nombre,
						data:[cantidadTrabaja, cantidadNoTrabaja]
				}
				serieTrabaja.push(valor);
			}
		}else{
			var partidos = $scope.encuesta.dataPartidos;
			for (var i = 0; i < partidos.length; i++) {
				var partido = candidatos[i];
				var cantidadTrabaja = trabaja[partido.id];
				var cantidadNoTrabaja = noTrabaja[partido.id];
				
				var valor = {
						name : partido.nombre,
						data:[cantidadTrabaja, cantidadNoTrabaja]
				}
				serieTrabaja.push(valor);
			}
			
		}		
		
	}
	
	/****************************************************************************/
	/****************************************************************************/
	/**********************CARGA DE DATOS DE GRAFICA INGRESOS*********************/
	/****************************************************************************/
	/****************************************************************************/
	
	if($scope.encuesta.preguntarIngresos){
		var serieIngresos = [];
		var mapIngresos1 = resultado.mapIngresos1;
		var mapIngresos2 = resultado.mapIngresos2;
		var mapIngresos3 = resultado.mapIngresos3;
		var mapIngresos4 = resultado.mapIngresos4;
		
		if($scope.encuesta.porCandidato){
			var candidatos = $scope.encuesta.dataCandidatos;
			for (var i = 0; i < candidatos.length; i++) {
				var candidato = candidatos[i];
				var cantidadIngreso1 = mapIngresos1[candidato.id];
				var cantidadIngreso2 = mapIngresos2[candidato.id];
				var cantidadIngreso3 = mapIngresos3[candidato.id];
				var cantidadIngreso4 = mapIngresos4[candidato.id];
				
				var valor = {
						name : candidato.nombre,
						data:[cantidadIngreso1, cantidadIngreso2, cantidadIngreso3, cantidadIngreso4]
				}
				serieIngresos.push(valor);
			}
		}else{
			var partidos = $scope.encuesta.dataPartidos;
			for (var i = 0; i < partidos.length; i++) {
				var partido = partidos[i];
				var cantidadIngreso1 = mapIngresos1[partido.id];
				var cantidadIngreso2 = mapIngresos2[partido.id];
				var cantidadIngreso3 = mapIngresos3[partido.id];
				var cantidadIngreso4 = mapIngresos4[partido.id];
				
				var valor = {
						name : candidato.nombre,
						data:[cantidadIngreso1, cantidadIngreso2, cantidadIngreso3, cantidadIngreso4]
				}
				serieIngresos.push(valor);
			}
		}
	}
	
	/****************************************************************************/
	/****************************************************************************/
	/**********************CARGA DE DATOS DE GRAFICA POR GENERO******************/
	/****************************************************************************/
	/****************************************************************************/
	
	if($scope.encuesta.preguntarSexo){
		var serieGenero = [{
			name: "Votos",
		    colorByPoint: true,
		    data: []
		}];
		var drilldownGenero = {
				series:[]
		}
		var mapMasculino = resultado.mapGeneroMas;
		var mapOtro = resultado.mapGeneroOtro;
		var mapFemenino = resultado.mapGeneroFem;
		
		if($scope.encuesta.porCandidato){
			var mapCandidatos = resultado.mapCandidatos;
			var candidatos = $scope.encuesta.dataCandidatos;
			
			for(var i=0; i < candidatos.length; i++){
				var candidato = candidatos[i];
				var cantidad = mapCandidatos[candidato.id];
				var valor = {
		                name: candidato.nombre,
		                y: cantidad,
		                drilldown: candidato.id
		            }
				serieGenero[0].data.push(valor);
				
				var serie = {
						name: candidato.nombre,
						id: candidato.id,
						data:[]						
				}				
				
				var datoMasc = ['Masculino', mapMasculino[candidato.id]];
				var datoFem = ['Femenino', mapFemenino[candidato.id]];
				var datoOtro = ['Otro', mapOtro[candidato.id]];
				serie.data.push(datoMasc);				
				serie.data.push(datoFem);				
				serie.data.push(datoOtro);
				
				drilldownGenero.series.push(serie);
				
			}		
			
		}else{
			var mapPartidos = resultado.mapPartidos;
			var partidos = $scope.encuesta.dataPartidos;
			
			for(var i = 0; i < partidos.length; i++){
				var partido = partidos[i];
				var cantidad = mapPartidos[partido.id];
				var valor = {
		                name: partido.nombre,
		                y: cantidad,
		                drilldown: partido.id
		            }
				serieGenero[0].data.push(valor);
				
				var serie = {
						name: partido.nombre,
						id: partido.id,
						data:[]						
				}				
				
				var datoMasc = ['Masculino', mapMasculino[partido.id]];
				var datoFem = ['Femenino', mapFemenino[partido.id]];
				var datoOtro = ['Otro', mapOtro[partido.id]];
				serie.data.push(datoMasc);				
				serie.data.push(datoFem);				
				serie.data.push(datoOtro);
				
				drilldownGenero.series.push(serie);
			}
		}	
		
		
	}
	
	
	/******************************************************************************************************************/
	/******************************************************************************************************************/
	/************AQUI ES EN DONDE SE CARGAN LOS DATOS ANTERIORES DEPENDIENDO DE QUE GRAFICA SE SELECCIONA**************/
	/******************************************************************************************************************/
	/******************************************************************************************************************/
	
	$scope.changeChart = function(){
		
		switch ($scope.graficaSeleccionada.id) {
		case 1:
			/*Igualo a null a todas las demas graficas*/
			chartColumEdad = null;
			chartColumEducacion = null;
			
			chartPie = new Highcharts.Chart({
			    chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false,
			            type: 'pie',
			            renderTo: 'container',
			        },
			        title: {
			            text: 'Resultado encuesta (prueba)'
			        },
			        subtitle: {
			        	text: 'Total encuestados: '+ $scope.encuesta.cantidadRespuestas,
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
			break;
		case 2:
			//chartPie = null;
			chartColumEducacion = null;
			
			var chartColumEdad = new Highcharts.Chart({
			    chart: {
			    	type: 'column',
			    	renderTo: 'container2',
			    },
			    title: {
		            text: 'Votos por edad'
		        },
		        subtitle: {
		        	text: 'Total encuestados: '+ $scope.encuesta.cantidadRespuestas,
		        },
		        xAxis: {
		            categories: [
		                '18 a 23',
		                '24 a 30',
		                '31 a 50',
		                'Mas de 50',
		               
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: 'Cantidad de votos'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b> {point.y}</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: serieEdad
		        
			})
			
			break;
		case 3:
			//chartPie = null;
			chartColumEdad = null;
			var chartColumEducacion = new Highcharts.Chart({
			    chart: {
			    	type: 'column',
			    	renderTo: 'container3',
			    },
			    title: {
		            text: 'Votos por nivel estudio'
		        },
		        subtitle: {
		        	text: 'Total encuestados: '+ $scope.encuesta.cantidadRespuestas,
		        },
		        xAxis: {
		            categories: [
		                'Primaria',
		                'Secundaria',
		                'Terciario',
		                'No sabe',
		               
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: 'Cantidad de votos'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b> {point.y}</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: serieEstudio
		        
			})
			
			break;
		case 4:
			//chartPie = null;
			chartColumEdad = null;
			chartColumEducacion = null;
			
			var chartColumLista = new Highcharts.Chart({
				chart: {
		            renderTo: 'container4',
		            type: 'column'            
		        },
		        title: {
		            text: 'Votos con detalles de listas'
		        },
		        subtitle: {
		            text: 'Click en las columnas para ver las listas'
		        },
		        xAxis: {
		            type: 'category'
		        },
		        yAxis: {
		            title: {
		                text: 'Total de votos'
		            }
		        },
		        legend: {
		            enabled: false
		        },
		        plotOptions: {
		            series: {
		                borderWidth: 0,
		                dataLabels: {
		                    enabled: true
		                }
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
		            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y}</b> votos<br/>'
		        },
		        series : serieLista,
		        drilldown : drilldownLista,
			});
			
			break;
		case 5:
			//chartPie = null;
			chartColumEducacion = null;
			chartColumEdad = null;
			
			var chartColumTrabaja = new Highcharts.Chart({
			    chart: {
			    	type: 'column',
			    	renderTo: 'container5',
			    },
			    title: {
		            text: 'Votos por actividad laboral'
		        },
		        subtitle: {
		        	text: 'Total encuestados: '+ $scope.encuesta.cantidadRespuestas,
		        },
		        xAxis: {
		            categories: [
		                'Trabaja',
		                'No trabaja'		               
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: 'Cantidad de votos'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b> {point.y}</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: serieTrabaja
		        
			})
			
			break;
		case 6:
			//chartPie = null;
			chartColumEducacion = null;
			chartColumEdad = null;
			chartColumTrabaja = null;
			
			var chartColumIngresos = new Highcharts.Chart({
			    chart: {
			    	type: 'column',
			    	renderTo: 'container6',
			    },
			    title: {
		            text: 'Votos segun rangos de ingresos'
		        },
		        subtitle: {
		        	text: 'Total encuestados: '+ $scope.encuesta.cantidadRespuestas,
		        },
		        xAxis: {
		            categories: [
		                'menos de 10.000',
		                'de 10.000 a 21.365',
		                'de 21.366 a 40.000',
		                'mas de 40.000'
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: 'Cantidad de votos'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b> {point.y}</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: serieIngresos
		        
			})
			
			break;
		case 7:
			//chartPie = null;
			chartColumEducacion = null;
			chartColumEdad = null;
			chartColumTrabaja = null;
			chartColumIngresos = null;
			
			var chartColumIngresos = new Highcharts.Chart({
			    chart: {
			    	type: 'pie',
			    	renderTo: 'container7',
			    },
			    title: {
		            text: 'Votos segun genero'
		        },
		        subtitle: {
		        	text: 'Total encuestados: '+ $scope.encuesta.cantidadRespuestas,
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
		        series: serieGenero,
		        drilldown : drilldownGenero
		        
			})
			
			break;
		default:
			break;
		}
		
		
	}
	
}])
.directive('onCarouselChange', function ($parse) {
  return {
    require: 'carousel',
    link: function (scope, element, attrs, carouselCtrl) {
      var fn = $parse(attrs.onCarouselChange);
      var origSelect = carouselCtrl.select;
      carouselCtrl.select = function (nextSlide, direction) {
        if (nextSlide !== this.currentSlide) {
          fn(scope, {
            nextSlide: nextSlide,
            direction: direction,
          });
        }
        return origSelect.apply(this, arguments);
      };
    }
  };
})



