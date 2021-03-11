package jreader2.web.controller.rest;

import jreader2.domain.Post;
import jreader2.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public List<Post> list(OAuth2AuthenticationToken auth) {
        return postService.list(auth.getPrincipal().getAttribute("email"));
    }

}
