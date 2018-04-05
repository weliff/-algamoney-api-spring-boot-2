package com.algamoney.api.repository.reactive;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Repository
public class CategoriaRepositoryReactive implements ReactiveCrudRepository<Categoria, Long> {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final Scheduler scheduler = Schedulers.newParallel("CategoriaRepository" );

    @Override
    public <S extends Categoria> Mono<S> save(S entity) {
        return Mono.defer(() -> Mono.just(categoriaRepository.save(entity)))
                .subscribeOn(scheduler)
                .log();
    }

    @Override
    public <S extends Categoria> Flux<S> saveAll(Iterable<S> entities) {
        return Flux.defer(() -> Flux.fromIterable(categoriaRepository.saveAll(entities)))
                .subscribeOn(scheduler)
                .log();

    }

    @Override
    public <S extends Categoria> Flux<S> saveAll(Publisher<S> entityStream) {
        return saveAll(Flux.from(entityStream).toIterable());
    }

    @Override
    public Mono<Categoria> findById(Long id) {
        return Mono.defer(() -> Mono.justOrEmpty(categoriaRepository.findById(id)))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Categoria> findById(Publisher<Long> id) {
        return Mono.from(id).flatMap(this::findById);
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        return Mono.defer(() -> Mono.just(categoriaRepository.existsById(id)))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<Long> id) {
        return Mono.from(id)
                .flatMap(this::existsById);
    }

    @Override
    public Flux<Categoria> findAll() {
        return Flux.defer(() -> Flux.fromIterable(categoriaRepository.findAll()))
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<Categoria> findAllById(Iterable<Long> longs) {
        return Flux.defer(() -> Flux.fromIterable(categoriaRepository.findAllById(longs)))
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<Categoria> findAllById(Publisher<Long> idStream) {
        return findAllById(Flux.from(idStream).toIterable());
    }

    @Override
    public Mono<Long> count() {
        return Mono.defer(() -> Mono.just(categoriaRepository.count()))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return Mono.defer(() -> {
            categoriaRepository.deleteById(aLong);
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }

    @Override
    public Mono<Void> deleteById(Publisher<Long> id) {
        return Mono.just(id)
                .flatMap(this::deleteById);
    }

    @Override
    public Mono<Void> delete(Categoria entity) {
        return Mono.defer(() -> {
            categoriaRepository.delete(entity);
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends Categoria> entities) {
        return Mono.defer(() -> {
            categoriaRepository.deleteAll(entities);
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends Categoria> entityStream) {
        return deleteAll(Flux.from(entityStream).toIterable());
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.defer(() -> {
            categoriaRepository.deleteAll();
            return Mono.empty();
        }).subscribeOn(scheduler).then();
    }
}
