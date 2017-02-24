(function() {
    'use strict';
    angular
        .module('agendaApp')
        .factory('Comision', Comision);

    Comision.$inject = ['$resource'];

    function Comision ($resource) {
        var resourceUrl =  'api/comisions/:id';

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
