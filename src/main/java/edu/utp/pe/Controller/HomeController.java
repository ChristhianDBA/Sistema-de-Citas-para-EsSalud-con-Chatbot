package edu.utp.pe.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("titleForm","EsSalud");
        return "index";
    }

    @GetMapping(value = "/consultar")
    public String consultar() {
        return "consultarcita";
    }

    @GetMapping(value = "/editar")
    public String editar() {
        return "editar";
    }


}
