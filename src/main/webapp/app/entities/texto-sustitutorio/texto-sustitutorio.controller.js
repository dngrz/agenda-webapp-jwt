(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('TextoSustitutorioController', TextoSustitutorioController);

    TextoSustitutorioController.$inject = ['TextoSustitutorio'];

    function TextoSustitutorioController(TextoSustitutorio) {

        var vm = this;

        vm.textoSustitutorios = [];

        loadAll();

        function loadAll() {
            TextoSustitutorio.query(function(result) {
                vm.textoSustitutorios = result;
                vm.searchQuery = null;
            });
        }
    }
})();
