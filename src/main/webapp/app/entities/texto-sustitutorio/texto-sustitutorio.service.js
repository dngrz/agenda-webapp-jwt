(function() {
    'use strict';
    angular
        .module('agendaApp')
        .factory('TextoSustitutorio', TextoSustitutorio);

    TextoSustitutorio.$inject = ['$resource', 'DateUtils'];

    function TextoSustitutorio ($resource, DateUtils) {
        var resourceUrl =  'api/texto-sustitutorios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaAdjunto = DateUtils.convertDateTimeFromServer(data.fechaAdjunto);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
