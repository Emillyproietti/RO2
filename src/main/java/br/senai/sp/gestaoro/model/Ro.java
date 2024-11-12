package br.senai.sp.gestaoro.model;

import br.senai.sp.gestaoro.enums.Encerramento;
import br.senai.sp.gestaoro.enums.TipoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Aluno aluno;

    @ManyToOne
    private Professor professor;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dtRo;

    private TipoRegistro tipoRegistro;

    private String uc;

    @Lob
    @Column( length = 7500 )
    private String dsEncaminhamento;

    @Lob
    @Column( length = 7500 )
    private String dsMotivo;

    @Lob
    @Column( length = 7500 )
    private String dsObservacao;

    private Encerramento encerramento;
}
