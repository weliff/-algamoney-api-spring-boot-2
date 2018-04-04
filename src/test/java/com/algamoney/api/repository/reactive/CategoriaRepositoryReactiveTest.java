package com.algamoney.api.repository.reactive;

import com.algamoney.api.model.Categoria;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CategoriaRepositoryReactiveTest extends BaseReactiveRepositoryTest {

    @Autowired
    private CategoriaRepositoryReactive categoriaRepositoryReactive;

    @Test
    public void deveSalvarNovaCategoria() throws Exception {
        Categoria categoria = new Categoria(null, "Nova categoria");

        Mono<Categoria> categoriaMono = categoriaRepositoryReactive.save(categoria);

        StepVerifier.create(categoriaMono)
                .assertNext(c -> {
                    Assert.assertEquals(c.getNome(), categoria.getNome());
                    Assert.assertNotNull(c.getCodigo()); })
                .verifyComplete();
    }
}