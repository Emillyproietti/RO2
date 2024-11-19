package br.senai.sp.gestaoro.repository;


import br.senai.sp.gestaoro.model.Aluno;
import br.senai.sp.gestaoro.model.Home;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<Home, Long> {
}
