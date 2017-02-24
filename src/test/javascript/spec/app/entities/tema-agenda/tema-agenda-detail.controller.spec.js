'use strict';

describe('Controller Tests', function() {

    describe('TemaAgenda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTemaAgenda, MockSeccion, MockComision, MockAgenda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTemaAgenda = jasmine.createSpy('MockTemaAgenda');
            MockSeccion = jasmine.createSpy('MockSeccion');
            MockComision = jasmine.createSpy('MockComision');
            MockAgenda = jasmine.createSpy('MockAgenda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TemaAgenda': MockTemaAgenda,
                'Seccion': MockSeccion,
                'Comision': MockComision,
                'Agenda': MockAgenda
            };
            createController = function() {
                $injector.get('$controller')("TemaAgendaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agendaApp:temaAgendaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
