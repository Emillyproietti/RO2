package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.*;
import br.senai.sp.gestaoro.repository.AlunoRepository;
import br.senai.sp.gestaoro.repository.ProfessorRepository;
import br.senai.sp.gestaoro.repository.RoRepository;
import br.senai.sp.gestaoro.repository.TurmaRepository;
import br.senai.sp.gestaoro.service.JavaSmtpGmailSenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/ro")
public class RoController {

    @Autowired
    private RoRepository roRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private JavaSmtpGmailSenderService javaSmtpGmailSenderService;

    @GetMapping
    public String listagem(Model model) {

        // recupera o usuário logado getPrincipal
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Role> roles = (List<Role>) user.getRoles();


        Role Role = roles.get(0);

        List<Ro> listRoo = roRepository.findAll();
        model.addAttribute("ros", listRoo);

        if (!Objects.equals(Role.getName(), "AQV")) {
            Long id = user.getId();
            Professor professor = professorRepository.findByUserId(id);
            List<Ro> listRo = roRepository.findByProfessor(professor);
            model.addAttribute("ros", listRo);
        }

        return "ro/listagem";
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

        // recupera o usuário logado getPrincipal
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        //recupera a role
        List<Role> roles = (List<Role>) user.getRoles();
        Role role = roles.get(0);

        Long id = user.getId();
        Professor professor = professorRepository.findByUserId(id);

        if (!Objects.equals(role.getName(), "AQV")) {

            Long id2 = user.getId();
            Professor professor2 = professorRepository.findByUserId(id2);

            ro.setProfessor(professor2);
        }

        javaSmtpGmailSenderService.sendEmail(
                professor.getEmail(),
                "Comunicado SENAI",
                "Prezado(a)" +
                        " " + ro.getAluno().getNmResponsavel() +
                        " Informamos que seu(sua) filho(a)," +
                        " " + ro.getAluno().getNome() +
                        ", recebeu uma ocorrência no dia" +
                        " " + ro.getDtRo() +"." +
                        " Pedimos que retorne este e-mail confirmando o recebimento desta mensagem. É importante que o SENAI saiba que você está ciente da situação. Assim que confirmarmos, entraremos em contato para conversarmos sobre a situação.\n" +
                        "Atenciosamente,\n" +
                        "SENAI"
        );


        javaSmtpGmailSenderService.sendEmail(
                ro.getAluno().getEmailResponsavel(),
                "Comunicado SENAI",
                "Prezado(a)" +
                        " " + ro.getAluno().getNmResponsavel() +
                        " Informamos que seu(sua) filho(a)," +
                        " " + ro.getAluno().getNome() +
                        ", recebeu uma ocorrência no dia" +
                        " " + ro.getDtRo() +"." +
                        " Pedimos que retorne este e-mail confirmando o recebimento desta mensagem. É importante que o SENAI saiba que você está ciente da situação. Assim que confirmarmos, entraremos em contato para conversarmos sobre a situação.\n" +
                        "Atenciosamente,\n" +
                        "SENAI"
        );


        // Mensagem de sucesso
        attributes.addFlashAttribute("mensagem", "R.O salva com sucesso!");

        roRepository.save(ro);
        return "redirect:/ro";
    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model) {

        Ro ro = roRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        // Alunos cadastrados
        model.addAttribute("alunos", alunoRepository.findAll());

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
