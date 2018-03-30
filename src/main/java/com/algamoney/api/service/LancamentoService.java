package com.algamoney.api.service;

import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.LancamentoRepository;
import com.algamoney.api.repository.PessoaRepository;
import com.algamoney.api.service.exception.PessoaInexistenteOuInativoException;
import com.algamoney.api.model.Lancamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getCodigo());

        if(pessoa != null && pessoa.isInativo()){
            throw new PessoaInexistenteOuInativoException();
        }

        return lancamentoRepository.save(lancamento);
    }
}
