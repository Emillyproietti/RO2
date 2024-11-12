package br.senai.sp.gestaoro.repository;

import br.senai.sp.gestaoro.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
