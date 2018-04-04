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
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Long aLong) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<Long> id) {
        return null;
    }

    @Override
    public Flux<Categoria> findAll() {
        return null;
    }

    @Override
    public Flux<Categoria> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Flux<Categoria> findAllById(Publisher<Long> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<Long> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(Categoria entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends Categoria> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends Categoria> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
