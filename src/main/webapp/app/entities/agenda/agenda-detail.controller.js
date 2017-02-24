(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('AgendaDetailController', AgendaDetailController);

    AgendaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Agenda', 'TemaAgenda', 'User'];

    function AgendaDetailController($scope, $rootScope, $stateParams, previousState, entity, Agenda, TemaAgenda, User) {
        var vm = this;

        vm.agenda = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agendaApp:agendaUpdate', function(event, result) {
            vm.agenda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
