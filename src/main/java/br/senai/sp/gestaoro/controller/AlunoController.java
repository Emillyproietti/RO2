package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.Aluno;
import br.senai.sp.gestaoro.model.Ro;
import br.senai.sp.gestaoro.repository.AlunoRepository;
import br.senai.sp.gestaoro.repository.RoRepository;
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

import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private RoRepository roRepository;




    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("alunos", alunoRepository.findAll());
        return "aluno/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {

        model.addAttribute("turmas", turmaRepository.findAll());

        model.addAttribute("aluno", new Aluno());
        return "aluno/form-inserir";
    }

    @GetMapping("/ficha")
    public String ficha(Model model) {



        model.addAttribute("aluno", new Aluno());
        return "aluno/ficha";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Aluno aluno, BindingResult result, RedirectAttributes attributes) {


        if (result.hasErrors()) {
            return "aluno/form-inserir";
        }

        // Verifica se o id do aluno está preenchido
        if (aluno.getId() != null) {
            if (result.hasErrors()) {
                return "aluno/form-alterar";
            }
        }

        if(!aluno.getUser().getPassword().isEmpty()){
            aluno.getUser().setFirstName(aluno.getNome());
            aluno.getUser().setEmail(aluno.getEmail());
            aluno.getUser().setUsername(aluno.getEmail());
            aluno.getUser().setPassword(bCryptPasswordEncoder.encode(aluno.getUser().getPassword()));
            aluno.addRole(roleRepository);
        }


        // Mensagem de sucesso
        attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");

        alunoRepository.save(aluno);
        return "redirect:/aluno";
    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("aluno", aluno);
        return "aluno/form-alterar";
    }

    @GetMapping("/ficha/{id}")
    public String ficha(@PathVariable("id") Long id, Model model) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        List<Ro> ros = roRepository.findByAluno(aluno);
        model.addAttribute("ros", ros);

        model.addAttribute("aluno", aluno);
        return "aluno/ficha";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        alunoRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Aluno excluído com sucesso!");
        return "redirect:/aluno";
    }




}
