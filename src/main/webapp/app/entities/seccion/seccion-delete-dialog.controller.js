(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('SeccionDeleteController',SeccionDeleteController);

    SeccionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Seccion'];

    function SeccionDeleteController($uibModalInstance, entity, Seccion) {
        var vm = this;

        vm.seccion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Seccion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
