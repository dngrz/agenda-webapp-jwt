'use strict';

describe('Controller Tests', function() {

    describe('TextoSustitutorio Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTextoSustitutorio, MockTemaAgenda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTextoSustitutorio = jasmine.createSpy('MockTextoSustitutorio');
            MockTemaAgenda = jasmine.createSpy('MockTemaAgenda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TextoSustitutorio': MockTextoSustitutorio,
                'TemaAgenda': MockTemaAgenda
            };
            createController = function() {
                $injector.get('$controller')("TextoSustitutorioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agendaApp:textoSustitutorioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
