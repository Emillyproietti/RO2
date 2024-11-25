package br.senai.sp.gestaoro.api;

import br.senai.sp.gestaoro.model.Aluno;
import br.senai.sp.gestaoro.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AlunoApiController {

    @Autowired
    private AlunoRepository alunoRepository;

    // Recupera o curso do aluno
    @GetMapping("/aluno/{id}")
    public String getCursoAluno(@PathVariable Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        return aluno.getTurma().getCurso();
    }

}
