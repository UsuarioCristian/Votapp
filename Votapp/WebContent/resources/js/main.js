var app = angular.module("app", []);

app.controller('firstController', ['$scope','$http', function($scope, $http){
		
	$scope.updateResultado = function(){
		
		$http.get('http://localhost:8080/Votapp/services/consultoras/'+$scope.idConsultora)
			.success(function(data){
				$scope.nombreConsultora = data.nombre;
				$scope.descConsultora = data.descripcion;				
			})
			.error(function(data){
				$scope.nombreConsultora = "Request failed";
				$scope.descConsultora = "";
				
			})
	}
	
}])