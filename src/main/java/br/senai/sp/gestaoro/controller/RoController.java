package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.Ro;
import br.senai.sp.gestaoro.repository.AlunoRepository;
import br.senai.sp.gestaoro.repository.ProfessorRepository;
import br.senai.sp.gestaoro.repository.RoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ro")
public class RoController {

    @Autowired
    private RoRepository roRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("ros", roRepository.findAll());
        return "turma/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {

        // Alunos cadastrados
        model.addAttribute("alunos", alunoRepository.findAll());

        model.addAttribute("ro", new Ro());
        return "ro/form-inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Ro ro, BindingResult result, RedirectAttributes attributes) {


        if (result.hasErrors()) {
            return "ro/form-inserir";
        }

        // Verifica se o id do aluno está preenchido
        if (ro.getId() != null) {
            if (result.hasErrors()) {
                return "ro/form-alterar";
            }
        }


        // Mensagem de sucesso
        attributes.addFlashAttribute("mensagem", "R.O salva com sucesso!");

        roRepository.save(ro);
        return "redirect:/ro";
    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        Ro ro = roRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        model.addAttribute("ro", ro);
        return "ro/form-alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        roRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "R.O excluída com sucesso!");
        return "redirect:/ro";
    }


}
