package br.senai.sp.gestaoro.repository;


import br.senai.sp.gestaoro.model.Ro;
import br.senai.sp.gestaoro.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoRepository extends JpaRepository<Ro, Long> {
}
