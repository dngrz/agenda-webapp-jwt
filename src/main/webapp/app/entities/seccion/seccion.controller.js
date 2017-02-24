(function() {
    'use strict';

    angular
        .module('agendaApp')
        .controller('SeccionController', SeccionController);

    SeccionController.$inject = ['Seccion'];

    function SeccionController(Seccion) {

        var vm = this;

        vm.seccions = [];

        loadAll();

        function loadAll() {
            Seccion.query(function(result) {
                vm.seccions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
