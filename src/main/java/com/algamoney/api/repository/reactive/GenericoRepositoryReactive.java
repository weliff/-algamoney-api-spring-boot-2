package com.algamoney.api.repository.reactive;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class GenericoRepositoryReactive<T, ID> implements ReactiveCrudRepository<T, ID> {

    @Autowired
    private JpaRepository<T, ID> jpaRepository;

    public GenericoRepositoryReactive(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private static final Scheduler scheduler = Schedulers.newParallel("ReactiveRepository");

    @Override
    public <S extends T> Mono<S> save(S entity) {
        return Mono.defer(() -> Mono.just(jpaRepository.save(entity)))
                .subscribeOn(scheduler)
                .log();
    }

    @Override
    public <S extends T> Flux<S> saveAll(Iterable<S> entities) {
        return Flux.defer(() -> Flux.fromIterable(jpaRepository.saveAll(entities)))
                .subscribeOn(scheduler)
                .log();

    }

    @Override
    public <S extends T> Flux<S> saveAll(Publisher<S> entityStream) {
        return saveAll(Flux.from(entityStream).toIterable());
    }

    @Override
    public Mono<T> findById(ID id) {
        return Mono.defer(() -> Mono.justOrEmpty(jpaRepository.findById(id)))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<T> findById(Publisher<ID> id) {
        return Mono.from(id).flatMap(this::findById);
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return Mono.defer(() -> Mono.just(jpaRepository.existsById(id)))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> id) {
        return Mono.from(id)
                .flatMap(this::existsById);
    }

    @Override
    public Flux<T> findAll() {
        return Flux.defer(() -> Flux.fromIterable(jpaRepository.findAll()))
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAllById(Iterable<ID> ids) {
        return Flux.defer(() -> Flux.fromIterable(jpaRepository.findAllById(ids)))
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAllById(Publisher<ID> idStream) {
        return findAllById(Flux.from(idStream).toIterable());
    }

    @Override
    public Mono<Long> count() {
        return Mono.defer(() -> Mono.just(jpaRepository.count()))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return Mono.defer(() -> {
            jpaRepository.deleteById(id);
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> id) {
        return Mono.just(id)
                .flatMap(this::deleteById);
    }

    @Override
    public Mono<Void> delete(T entity) {
        return Mono.defer(() -> {
            jpaRepository.delete(entity);
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> entities) {
        return Mono.defer(() -> {
            jpaRepository.deleteAll(entities);
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends T> entityStream) {
        return deleteAll(Flux.from(entityStream).toIterable());
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.defer(() -> {
            jpaRepository.deleteAll();
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }
}
