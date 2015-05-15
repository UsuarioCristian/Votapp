var app = angular.module("app", []) ;

app.controller('Main', ['$scope','$http', function($scope, $http){
	
	$scope.register = function(username, password){
		var user = {
				username: username,
				password: password
		}
		$http.post('http://localhost:8080/Votapp/services/usuario/crear', user)
		.success(function(data){
			$scope.username = data.username;
			$scope.password = data.password;				
		})
		.error(function(data){
			$scope.nombreConsultora = "Request failed";
			$scope.descConsultora = "";
		})
}
	
}])