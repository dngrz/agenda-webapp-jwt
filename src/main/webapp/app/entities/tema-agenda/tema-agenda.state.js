(function() {
    'use strict';

    angular
        .module('agendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tema-agenda', {
            parent: 'entity',
            url: '/tema-agenda?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TemaAgenda'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tema-agenda/tema-agenda.html',
                    controller: 'TemaAgendaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('tema-agenda-detail', {
            parent: 'tema-agenda',
            url: '/tema-agenda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TemaAgenda'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tema-agenda/tema-agenda-detail.html',
                    controller: 'TemaAgendaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TemaAgenda', function($stateParams, TemaAgenda) {
                    return TemaAgenda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tema-agenda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tema-agenda-detail.edit', {
            parent: 'tema-agenda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-agenda/tema-agenda-dialog.html',
                    controller: 'TemaAgendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TemaAgenda', function(TemaAgenda) {
                            return TemaAgenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tema-agenda.new', {
            parent: 'tema-agenda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-agenda/tema-agenda-dialog.html',
                    controller: 'TemaAgendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                titulo: null,
                                numero: null,
                                contenido: null,
                                url: null,
                                temaActivo: null,
                                estadoTema: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tema-agenda', null, { reload: 'tema-agenda' });
                }, function() {
                    $state.go('tema-agenda');
                });
            }]
        })
        .state('tema-agenda.edit', {
            parent: 'tema-agenda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-agenda/tema-agenda-dialog.html',
                    controller: 'TemaAgendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TemaAgenda', function(TemaAgenda) {
                            return TemaAgenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tema-agenda', null, { reload: 'tema-agenda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tema-agenda.delete', {
            parent: 'tema-agenda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tema-agenda/tema-agenda-delete-dialog.html',
                    controller: 'TemaAgendaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TemaAgenda', function(TemaAgenda) {
                            return TemaAgenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tema-agenda', null, { reload: 'tema-agenda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
