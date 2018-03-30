package com.algamoney.api.repository.lancamento;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.repository.filter.LancamentoFilter;
import com.algamoney.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")// and #oauth2.hasScope('read')")
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);

}
