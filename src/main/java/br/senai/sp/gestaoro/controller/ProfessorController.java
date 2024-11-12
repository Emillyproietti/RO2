package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.Aluno;
import br.senai.sp.gestaoro.model.Professor;
import br.senai.sp.gestaoro.repository.AlunoRepository;
import br.senai.sp.gestaoro.repository.ProfessorRepository;
import br.senai.sp.gestaoro.repository.RoleRepository;
import br.senai.sp.gestaoro.repository.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TurmaRepository turmaRepository;




    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("professores", professorRepository.findAll());
        return "professor/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {

        model.addAttribute("turmas", turmaRepository.findAll());

        model.addAttribute("professor", new Professor());
        return "professor/form-inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Professor professor, BindingResult result, RedirectAttributes attributes) {


        if (result.hasErrors()) {
            return "professor/form-inserir";
        }

        // Verifica se o id do aluno está preenchido
        if (professor.getId() != null) {
            if (result.hasErrors()) {
                return "professor/form-alterar";
            }
        }

        if(!professor.getUser().getPassword().isEmpty()){
            professor.getUser().setFirstName(professor.getNome());
            professor.getUser().setEmail(professor.getEmail());
            professor.getUser().setUsername(professor.getEmail());
            professor.getUser().setPassword(bCryptPasswordEncoder.encode(professor.getUser().getPassword()));
            professor.addRole(roleRepository);
        }


        // Mensagem de sucesso
        attributes.addFlashAttribute("mensagem", "Professor salvo com sucesso!");

        professorRepository.save(professor);
        return "redirect:/professor";
    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("professor", professor);
        return "professor/form-alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        professorRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Professor excluído com sucesso!");
        return "redirect:/professor";
    }


}
