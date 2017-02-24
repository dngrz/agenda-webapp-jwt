(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('ComisionDialogController', ComisionDialogController);

    ComisionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comision', 'TemaAgenda'];

    function ComisionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Comision, TemaAgenda) {
        var vm = this;

        vm.comision = entity;
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
            if (vm.comision.id !== null) {
                Comision.update(vm.comision, onSaveSuccess, onSaveError);
            } else {
                Comision.save(vm.comision, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agendaApp:comisionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
