package jreader2.app.web.controller;

import jreader2.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final UserService userService;

    @GetMapping("/")
    public String index(OAuth2AuthenticationToken auth, Model model) {
        String email = auth.getPrincipal().getAttribute("email");
        if (userService.isAuthorized(email)) {
            model.addAttribute("name", email);
            return "reader";
        } else {
            return "forbidden";
        }
    }

}
