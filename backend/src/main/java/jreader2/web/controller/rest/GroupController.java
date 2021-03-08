package jreader2.web.controller.rest;

import jreader2.domain.Group;
import jreader2.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public Group create(OAuth2AuthenticationToken auth, @RequestBody Group group) {
        return groupService.create(auth.getPrincipal().getAttribute("email"), group);
    }

    @DeleteMapping("/{groupId}")
    public void delete(OAuth2AuthenticationToken auth, @PathVariable long groupId) {
        groupService.delete(auth.getPrincipal().getAttribute("email"), groupId);
    }

    @GetMapping
    public List<Group> list(OAuth2AuthenticationToken auth) {
        return groupService.list(auth.getPrincipal().getAttribute("email"));
    }

}
