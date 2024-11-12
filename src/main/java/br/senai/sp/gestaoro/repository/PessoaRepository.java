package br.senai.sp.gestaoro.repository;

import br.senai.sp.gestaoro.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
