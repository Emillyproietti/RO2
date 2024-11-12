package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.User;
import br.senai.sp.gestaoro.repository.UserRepository;
import br.senai.sp.gestaoro.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public String listagem(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "user/listagem";
    }

    @GetMapping("/form-inserir")
    public String formInserir(Model model){
        model.addAttribute("user", new User());
        return "user/form-inserir";
    }

    @GetMapping("/form-alterar/{id}")
    public String formAlterar(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/form-alterar";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid User user,
            BindingResult result,
            RedirectAttributes attributes,
            @RequestParam("foto") MultipartFile multipartFile
    ) throws IOException
    {

        if(result.hasErrors()){
            return "user/form-inserir";
        }


        String extensao = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);


        // fileName = user.getId() + "." + extensao;
        String fileName = user.getId() + "." + extensao;

        user.setImage(fileName);

        userRepository.save(user);


        String uploadPasta =  "src/main/resources/static/assets/img/fotos-usuarios";

        FileUploadUtil.saveFile(uploadPasta, fileName, multipartFile);

        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");

        return "redirect:/user";
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes){
        userRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
        return "redirect:/user";
    }
}
