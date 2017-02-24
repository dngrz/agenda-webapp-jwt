(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('TextoSustitutorioDialogController', TextoSustitutorioDialogController);

    TextoSustitutorioDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TextoSustitutorio', 'TemaAgenda'];

    function TextoSustitutorioDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TextoSustitutorio, TemaAgenda) {
        var vm = this;

        vm.textoSustitutorio = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.textoSustitutorio.id !== null) {
                TextoSustitutorio.update(vm.textoSustitutorio, onSaveSuccess, onSaveError);
            } else {
                TextoSustitutorio.save(vm.textoSustitutorio, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agendaApp:textoSustitutorioUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaAdjunto = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
