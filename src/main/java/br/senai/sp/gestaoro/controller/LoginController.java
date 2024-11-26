package br.senai.sp.gestaoro.controller;

import br.senai.sp.gestaoro.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(){
        return "login/form-login";
    }


        @GetMapping("/solicitar-recuperacao-senha")
        public String formRecuperarSenha(Model model) {
            model.addAttribute("usuario", new User());
            return "login/recuperar-senha";
        }

        @PostMapping("/solicitar-recuperacao-senha")
        public String enviarEmailRecuperacao(
                @RequestParam("email") String email) {
            // Implementar lógica para enviar o email com o código de verificação
            return "login/form-informar-email"; // Página de confirmação de envio do email
        }

        @GetMapping("/verificar-codigo")
        public String verificarCodigo(@RequestParam("codigo") String codigo) {
            // Validar o código
            if (codigoValido(codigo)) {
                return "login/recuperar-senha";
            } else {
                return "login/esquecisenha";
            }
        }

        @PostMapping("/definir-nova-senha")
        public String atualizarSenha(
                @RequestParam("novaSenha") String novaSenha,
                // Outros parâmetros necessários
        ) {
            // Atualizar a senha do usuário no banco de dados
            return "login/form-informar-senha";
        }

}
