package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.Ro;
import br.senai.sp.gestaoro.repository.RoRepository;
import br.senai.sp.gestaoro.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ro")
public class RoController {
    @Autowired
    private RoRepository roRepository;

    @GetMapping
    public String listagem(Model model){
        model.addAttribute("ro", roRepository.findAll());
        return "ro/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model){
        model.addAttribute("ro", new Ro());

        return "ro/form-inserir";
    }

}
