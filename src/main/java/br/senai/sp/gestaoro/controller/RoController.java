package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.Ro;
import br.senai.sp.gestaoro.repository.TurmaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ro")
public class RoController {


    private final TurmaRepository turmaRepository;

    public RoController(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model){
        model.addAttribute("turmas", turmaRepository.findAll());
        model.addAttribute("ro", new Ro());
        return "ro/form-inserir";
    }


}
