package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.Turma;
import br.senai.sp.gestaoro.repository.TurmaRepository;
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
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("turmas", turmaRepository.findAll());
        return "turma/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {

        model.addAttribute("turma", new Turma());
        return "turma/form-inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Turma turma, BindingResult result, RedirectAttributes attributes) {


        if (result.hasErrors()) {
            return "turma/form-inserir";
        }

        // Verifica se o id do aluno está preenchido
        if (turma.getId() != null) {
            if (result.hasErrors()) {
                return "turma/form-alterar";
            }
        }


        // Mensagem de sucesso
        attributes.addFlashAttribute("mensagem", "Turma salva com sucesso!");

        turmaRepository.save(turma);
        return "redirect:/turma";
    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        Turma turma = turmaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        model.addAttribute("turma", turma);
        return "turma/form-alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        turmaRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Turma excluída com sucesso!");
        return "redirect:/turma";
    }


}
