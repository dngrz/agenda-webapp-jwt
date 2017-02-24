(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('TemaAgendaDialogController', TemaAgendaDialogController);

    TemaAgendaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TemaAgenda', 'Seccion', 'Comision', 'Agenda'];

    function TemaAgendaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TemaAgenda, Seccion, Comision, Agenda) {
        var vm = this;

        vm.temaAgenda = entity;
        vm.clear = clear;
        vm.save = save;
        vm.seccions = Seccion.query();
        vm.comisions = Comision.query();
        vm.agenda = Agenda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.temaAgenda.id !== null) {
                TemaAgenda.update(vm.temaAgenda, onSaveSuccess, onSaveError);
            } else {
                TemaAgenda.save(vm.temaAgenda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agendaApp:temaAgendaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
