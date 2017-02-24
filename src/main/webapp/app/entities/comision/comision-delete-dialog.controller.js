(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('ComisionDeleteController',ComisionDeleteController);

    ComisionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Comision'];

    function ComisionDeleteController($uibModalInstance, entity, Comision) {
        var vm = this;

        vm.comision = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Comision.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
