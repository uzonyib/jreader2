package jreader2.web.controller.rest;

import jreader2.domain.Post;
import jreader2.domain.PostFilter;
import jreader2.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(value = {
            "/posts",
            "/groups/{groupId}/posts",
            "/groups/{groupId}/subscriptions/{subscriptionId}/posts"
    })
    public List<Post> list(OAuth2AuthenticationToken auth, @PathVariable Optional<Long> groupId,
                           @PathVariable Optional<Long> subscriptionId) {
        PostFilter filter = PostFilter.builder()
                .email(auth.getPrincipal().getAttribute("email"))
                .groupId(groupId)
                .subscriptionId(subscriptionId)
                .limit(20)
                .build();
        return postService.list(filter);
    }

}
