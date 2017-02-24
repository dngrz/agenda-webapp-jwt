(function() {
    'use strict';

    angular
        .module('agendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seccion', {
            parent: 'entity',
            url: '/seccion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Seccions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seccion/seccions.html',
                    controller: 'SeccionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('seccion-detail', {
            parent: 'seccion',
            url: '/seccion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Seccion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seccion/seccion-detail.html',
                    controller: 'SeccionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Seccion', function($stateParams, Seccion) {
                    return Seccion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'seccion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('seccion-detail.edit', {
            parent: 'seccion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion/seccion-dialog.html',
                    controller: 'SeccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seccion', function(Seccion) {
                            return Seccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seccion.new', {
            parent: 'seccion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion/seccion-dialog.html',
                    controller: 'SeccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subtitulo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('seccion', null, { reload: 'seccion' });
                }, function() {
                    $state.go('seccion');
                });
            }]
        })
        .state('seccion.edit', {
            parent: 'seccion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion/seccion-dialog.html',
                    controller: 'SeccionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Seccion', function(Seccion) {
                            return Seccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seccion', null, { reload: 'seccion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seccion.delete', {
            parent: 'seccion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seccion/seccion-delete-dialog.html',
                    controller: 'SeccionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Seccion', function(Seccion) {
                            return Seccion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seccion', null, { reload: 'seccion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
