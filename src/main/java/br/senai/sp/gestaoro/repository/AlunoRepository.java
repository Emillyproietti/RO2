package br.senai.sp.gestaoro.repository;


import br.senai.sp.gestaoro.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
