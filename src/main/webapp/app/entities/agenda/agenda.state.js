(function() {
    'use strict';

    angular
        .module('agendaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('agenda', {
            parent: 'entity',
            url: '/agenda?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Agenda'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agenda/agenda.html',
                    controller: 'AgendaController',
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
        .state('agenda-detail', {
            parent: 'agenda',
            url: '/agenda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Agenda'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agenda/agenda-detail.html',
                    controller: 'AgendaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Agenda', function($stateParams, Agenda) {
                    return Agenda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'agenda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('agenda-detail.edit', {
            parent: 'agenda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agenda/agenda-dialog.html',
                    controller: 'AgendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agenda', function(Agenda) {
                            return Agenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agenda.new', {
            parent: 'agenda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agenda/agenda-dialog.html',
                    controller: 'AgendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                fecSesion: null,
                                legislatura: null,
                                tipoLegislatura: null,
                                indPublicado: null,
                                estadoAgenda: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('agenda', null, { reload: 'agenda' });
                }, function() {
                    $state.go('agenda');
                });
            }]
        })
        .state('agenda.edit', {
            parent: 'agenda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agenda/agenda-dialog.html',
                    controller: 'AgendaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agenda', function(Agenda) {
                            return Agenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agenda', null, { reload: 'agenda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agenda.delete', {
            parent: 'agenda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agenda/agenda-delete-dialog.html',
                    controller: 'AgendaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Agenda', function(Agenda) {
                            return Agenda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agenda', null, { reload: 'agenda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
