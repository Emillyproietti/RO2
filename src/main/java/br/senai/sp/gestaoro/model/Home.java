package br.senai.sp.gestaoro.model;

import br.senai.sp.gestaoro.repository.RoleRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
// A de Aluno
@DiscriminatorValue(value = "HOME")
public class Home extends Pessoa{
    public static final String ROLE_HOME= "HOME";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_HOME);
        this.getUser().getRoles().add(role);
    }
}
