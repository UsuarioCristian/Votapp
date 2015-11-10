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
		function(){
			/*Error del timeout*/
			console.log('Error $timeout')
		}		
	);
	
	$scope.goToEncuesta = function(encuesta){
		$state.go('encuesta', {encuesta : encuesta, eleccionId : $scope.eleccion.id});
	}
	
	$scope.$apply();
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
	console.log($scope.encuesta);
	
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
				serieEdad.push(valor);
			}
		}	
		
		
	}
	/******************************************************************************************************************/
	/******************************************************************************************************************/
	/************AQUI ES EN DONDE SE CARGAN LOS DATOS ANTERIORES DEPENDIENDO DE QUE GRAFICA SE SELECCIONA**************/
	/******************************************************************************************************************/
	/******************************************************************************************************************/
	
	$scope.changeChart = function(){
		if($scope.graficaSeleccionada.id === 1){
			
			/*Igualo a null a todas las demas graficas*/
			chartColumEdad = null;
			
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
		}else
			if($scope.graficaSeleccionada.id === 2){
				chartPie = null;
				
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
			}
	}
	
}])



