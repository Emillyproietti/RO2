package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.Aqv;
import br.senai.sp.gestaoro.model.Professor;
import br.senai.sp.gestaoro.repository.AqvRepository;
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
@RequestMapping("/aqv")
public class AqvController {

    @Autowired
    private AqvRepository aqvRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;



    @GetMapping
    public String listagem(Model model) {
        model.addAttribute("aqvs", aqvRepository.findAll());
        return "aqv/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model) {
        model.addAttribute("aqv", new Aqv());
        return "aqv/form-inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Aqv aqv, BindingResult result, RedirectAttributes attributes) {


        if (result.hasErrors()) {
            return "aqv/form-inserir";
        }

        // Verifica se o id do aluno está preenchido
        if (aqv.getId() != null) {
            if (result.hasErrors()) {
                return "aluno/form-alterar";
            }
        }

        if(!aqv.getUser().getPassword().isEmpty()){
            aqv.getUser().setFirstName(aqv.getNome());
            aqv.getUser().setEmail(aqv.getEmail());
            aqv.getUser().setUsername(aqv.getEmail());
            aqv.getUser().setPassword(bCryptPasswordEncoder.encode(aqv.getUser().getPassword()));
            aqv.addRole(roleRepository);
        }


        // Mensagem de sucesso
        attributes.addFlashAttribute("mensagem", "AQV salvo com sucesso!");

        aqvRepository.save(aqv);
        return "redirect:/aqv";
    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {
        Aqv aqv = aqvRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("aqv", aqv);
        return "aqv/form-alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        aqvRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "AQV excluído com sucesso!");
        return "redirect:/aqv";
    }


}
