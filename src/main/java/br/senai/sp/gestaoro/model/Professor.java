package br.senai.sp.gestaoro.model;

import br.senai.sp.gestaoro.repository.RoleRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
// A de Aluno
@DiscriminatorValue(value = "PRO")
public class Professor extends Pessoa{
    public static final String ROLE_PROFESSOR = "PROFESSOR";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_PROFESSOR);
        this.getUser().getRoles().add(role);
    }
}
