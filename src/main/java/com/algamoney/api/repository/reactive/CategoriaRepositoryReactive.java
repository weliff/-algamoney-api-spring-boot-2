package com.algamoney.api.repository.reactive;

import com.algamoney.api.model.Categoria;
import com.algamoney.api.repository.CategoriaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRepositoryReactive extends GenericoRepositoryReactive<Categoria, Long> {

    public CategoriaRepositoryReactive(CategoriaRepository categoriaRepository) {
        super(categoriaRepository);
    }

}
