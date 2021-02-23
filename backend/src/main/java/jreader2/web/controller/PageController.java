package jreader2.web.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping(value = {"/", "/reader/**"})
    public String index(OAuth2AuthenticationToken auth, Model model) {
        String email = auth.getPrincipal().getAttribute("email");
        model.addAttribute("name", email);
        return "index";
    }

}
