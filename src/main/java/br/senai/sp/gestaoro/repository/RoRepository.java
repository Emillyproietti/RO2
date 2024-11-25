package br.senai.sp.gestaoro.repository;

import br.senai.sp.gestaoro.model.Professor;
import br.senai.sp.gestaoro.model.Ro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoRepository extends JpaRepository<Ro, Long> {
    //Lista de RO por professor
    List<Ro> findByProfessor(Professor professor);
}
