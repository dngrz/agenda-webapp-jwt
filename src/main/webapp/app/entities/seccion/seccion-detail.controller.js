(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('SeccionDetailController', SeccionDetailController);

    SeccionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Seccion', 'TemaAgenda'];

    function SeccionDetailController($scope, $rootScope, $stateParams, previousState, entity, Seccion, TemaAgenda) {
        var vm = this;

        vm.seccion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agendaApp:seccionUpdate', function(event, result) {
            vm.seccion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
