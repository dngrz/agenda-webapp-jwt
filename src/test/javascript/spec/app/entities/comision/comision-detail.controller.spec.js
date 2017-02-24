'use strict';

describe('Controller Tests', function() {

    describe('Comision Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockComision, MockTemaAgenda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockComision = jasmine.createSpy('MockComision');
            MockTemaAgenda = jasmine.createSpy('MockTemaAgenda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Comision': MockComision,
                'TemaAgenda': MockTemaAgenda
            };
            createController = function() {
                $injector.get('$controller')("ComisionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agendaApp:comisionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
