package br.senai.sp.gestaoro.model;

import br.senai.sp.gestaoro.repository.RoleRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
// A de Aluno
@DiscriminatorValue(value = "AQV")
public class Aqv extends Pessoa{
    public static final String ROLE_AQV = "AQV";

    public void addRole(RoleRepository roleRepository) {
        Role role = roleRepository.findByName(ROLE_AQV);
        this.getUser().getRoles().add(role);
    }
}
