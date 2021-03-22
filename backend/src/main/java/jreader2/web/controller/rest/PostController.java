package jreader2.web.controller.rest;

import jreader2.domain.Post;
import jreader2.domain.PostFilter;
import jreader2.domain.PostUpdate;
import jreader2.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
                           @PathVariable Optional<Long> subscriptionId, @RequestParam Optional<String> sort) {
        PostFilter filter = PostFilter.builder()
                .email(auth.getPrincipal().getAttribute("email"))
                .groupId(groupId)
                .subscriptionId(subscriptionId)
                .ascendingOrder(sort.map(s -> !s.equals("desc")).orElse(true))
                .limit(20)
                .build();
        return postService.list(filter);
    }

    @PostMapping("/posts")
    public void update(OAuth2AuthenticationToken auth, @RequestBody List<PostUpdate> updates) {
        postService.update(auth.getPrincipal().getAttribute("email"), updates);
    }

}
