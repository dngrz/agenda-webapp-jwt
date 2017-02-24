(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('TemaAgendaDetailController', TemaAgendaDetailController);

    TemaAgendaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TemaAgenda', 'Seccion', 'Comision', 'Agenda'];

    function TemaAgendaDetailController($scope, $rootScope, $stateParams, previousState, entity, TemaAgenda, Seccion, Comision, Agenda) {
        var vm = this;

        vm.temaAgenda = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agendaApp:temaAgendaUpdate', function(event, result) {
            vm.temaAgenda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
