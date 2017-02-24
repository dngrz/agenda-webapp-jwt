(function() {
    'use strict';

    angular
        .module('agendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comision', {
            parent: 'entity',
            url: '/comision',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comisions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comision/comisions.html',
                    controller: 'ComisionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('comision-detail', {
            parent: 'comision',
            url: '/comision/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comision'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comision/comision-detail.html',
                    controller: 'ComisionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Comision', function($stateParams, Comision) {
                    return Comision.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comision',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comision-detail.edit', {
            parent: 'comision-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comision/comision-dialog.html',
                    controller: 'ComisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comision', function(Comision) {
                            return Comision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comision.new', {
            parent: 'comision',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comision/comision-dialog.html',
                    controller: 'ComisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comision', null, { reload: 'comision' });
                }, function() {
                    $state.go('comision');
                });
            }]
        })
        .state('comision.edit', {
            parent: 'comision',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comision/comision-dialog.html',
                    controller: 'ComisionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comision', function(Comision) {
                            return Comision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comision', null, { reload: 'comision' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comision.delete', {
            parent: 'comision',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comision/comision-delete-dialog.html',
                    controller: 'ComisionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Comision', function(Comision) {
                            return Comision.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comision', null, { reload: 'comision' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
