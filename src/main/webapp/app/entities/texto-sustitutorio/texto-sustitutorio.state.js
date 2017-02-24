(function() {
    'use strict';

    angular
        .module('agendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('texto-sustitutorio', {
            parent: 'entity',
            url: '/texto-sustitutorio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TextoSustitutorios'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/texto-sustitutorio/texto-sustitutorios.html',
                    controller: 'TextoSustitutorioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('texto-sustitutorio-detail', {
            parent: 'texto-sustitutorio',
            url: '/texto-sustitutorio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TextoSustitutorio'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/texto-sustitutorio/texto-sustitutorio-detail.html',
                    controller: 'TextoSustitutorioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TextoSustitutorio', function($stateParams, TextoSustitutorio) {
                    return TextoSustitutorio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'texto-sustitutorio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('texto-sustitutorio-detail.edit', {
            parent: 'texto-sustitutorio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/texto-sustitutorio/texto-sustitutorio-dialog.html',
                    controller: 'TextoSustitutorioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TextoSustitutorio', function(TextoSustitutorio) {
                            return TextoSustitutorio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('texto-sustitutorio.new', {
            parent: 'texto-sustitutorio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/texto-sustitutorio/texto-sustitutorio-dialog.html',
                    controller: 'TextoSustitutorioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                archivo: null,
                                fechaAdjunto: null,
                                url: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('texto-sustitutorio', null, { reload: 'texto-sustitutorio' });
                }, function() {
                    $state.go('texto-sustitutorio');
                });
            }]
        })
        .state('texto-sustitutorio.edit', {
            parent: 'texto-sustitutorio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/texto-sustitutorio/texto-sustitutorio-dialog.html',
                    controller: 'TextoSustitutorioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TextoSustitutorio', function(TextoSustitutorio) {
                            return TextoSustitutorio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('texto-sustitutorio', null, { reload: 'texto-sustitutorio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('texto-sustitutorio.delete', {
            parent: 'texto-sustitutorio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/texto-sustitutorio/texto-sustitutorio-delete-dialog.html',
                    controller: 'TextoSustitutorioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TextoSustitutorio', function(TextoSustitutorio) {
                            return TextoSustitutorio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('texto-sustitutorio', null, { reload: 'texto-sustitutorio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
