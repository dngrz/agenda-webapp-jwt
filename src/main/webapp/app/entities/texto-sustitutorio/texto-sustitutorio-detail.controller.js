(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('TextoSustitutorioDetailController', TextoSustitutorioDetailController);

    TextoSustitutorioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TextoSustitutorio', 'TemaAgenda'];

    function TextoSustitutorioDetailController($scope, $rootScope, $stateParams, previousState, entity, TextoSustitutorio, TemaAgenda) {
        var vm = this;

        vm.textoSustitutorio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agendaApp:textoSustitutorioUpdate', function(event, result) {
            vm.textoSustitutorio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
