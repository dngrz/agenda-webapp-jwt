(function() {
    'use strict';
    angular
        .module('agendaApp')
        .factory('Agenda', Agenda);

    Agenda.$inject = ['$resource', 'DateUtils'];

    function Agenda ($resource, DateUtils) {
        var resourceUrl =  'api/agenda/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecSesion = DateUtils.convertDateTimeFromServer(data.fecSesion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
