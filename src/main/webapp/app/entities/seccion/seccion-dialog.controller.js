(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('SeccionDialogController', SeccionDialogController);

    SeccionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Seccion', 'TemaAgenda'];

    function SeccionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Seccion, TemaAgenda) {
        var vm = this;

        vm.seccion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.temaagenda = TemaAgenda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.seccion.id !== null) {
                Seccion.update(vm.seccion, onSaveSuccess, onSaveError);
            } else {
                Seccion.save(vm.seccion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agendaApp:seccionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
