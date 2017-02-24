(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('AgendaDeleteController',AgendaDeleteController);

    AgendaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Agenda'];

    function AgendaDeleteController($uibModalInstance, entity, Agenda) {
        var vm = this;

        vm.agenda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Agenda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
