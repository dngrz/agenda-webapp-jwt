(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('AgendaDialogController', AgendaDialogController);

    AgendaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Agenda', 'TemaAgenda', 'User'];

    function AgendaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Agenda, TemaAgenda, User) {
        var vm = this;

        vm.agenda = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.temaagenda = TemaAgenda.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.agenda.id !== null) {
                Agenda.update(vm.agenda, onSaveSuccess, onSaveError);
            } else {
                Agenda.save(vm.agenda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agendaApp:agendaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fecSesion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
