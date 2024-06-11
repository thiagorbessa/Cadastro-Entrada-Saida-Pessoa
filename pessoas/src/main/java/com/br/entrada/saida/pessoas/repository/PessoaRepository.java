package com.br.entrada.saida.pessoas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.entrada.saida.pessoas.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,String>{

}
