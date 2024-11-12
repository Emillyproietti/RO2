package br.senai.sp.gestaoro.repository;

import br.senai.sp.gestaoro.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
