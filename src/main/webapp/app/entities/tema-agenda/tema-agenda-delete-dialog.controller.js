(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('TemaAgendaDeleteController',TemaAgendaDeleteController);

    TemaAgendaDeleteController.$inject = ['$uibModalInstance', 'entity', 'TemaAgenda'];

    function TemaAgendaDeleteController($uibModalInstance, entity, TemaAgenda) {
        var vm = this;

        vm.temaAgenda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TemaAgenda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
