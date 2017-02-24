(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('TextoSustitutorioDeleteController',TextoSustitutorioDeleteController);

    TextoSustitutorioDeleteController.$inject = ['$uibModalInstance', 'entity', 'TextoSustitutorio'];

    function TextoSustitutorioDeleteController($uibModalInstance, entity, TextoSustitutorio) {
        var vm = this;

        vm.textoSustitutorio = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TextoSustitutorio.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
