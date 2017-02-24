(function() {
    'use strict';
    angular
        .module('agendaApp')
        .factory('TemaAgenda', TemaAgenda);

    TemaAgenda.$inject = ['$resource'];

    function TemaAgenda ($resource) {
        var resourceUrl =  'api/tema-agenda/:id';

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
