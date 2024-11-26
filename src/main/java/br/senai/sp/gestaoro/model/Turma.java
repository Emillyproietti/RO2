package br.senai.sp.gestaoro.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique=true)
    private Long id;
    private String curso;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataConclusao;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    private List<Pessoa> pessoas;






}
