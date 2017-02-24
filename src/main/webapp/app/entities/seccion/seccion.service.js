(function() {
    'use strict';
    angular
        .module('agendaApp')
        .factory('Seccion', Seccion);

    Seccion.$inject = ['$resource'];

    function Seccion ($resource) {
        var resourceUrl =  'api/seccions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
