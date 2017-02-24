(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('ComisionDetailController', ComisionDetailController);

    ComisionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comision', 'TemaAgenda'];

    function ComisionDetailController($scope, $rootScope, $stateParams, previousState, entity, Comision, TemaAgenda) {
        var vm = this;

        vm.comision = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agendaApp:comisionUpdate', function(event, result) {
            vm.comision = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
