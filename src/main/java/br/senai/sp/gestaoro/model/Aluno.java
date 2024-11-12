package br.senai.sp.gestaoro.model;

import br.senai.sp.gestaoro.repository.RoleRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
// A de Aluno
@DiscriminatorValue(value = "ALU")
public class Aluno extends Pessoa{
    public static final String ROLE_ALUNO = "ALUNO";
    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_ALUNO);
        this.getUser().getRoles().add(role);
    }
}