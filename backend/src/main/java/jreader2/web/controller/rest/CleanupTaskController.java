package jreader2.web.controller.rest;

import jreader2.domain.Role;
import jreader2.service.PostCleanupService;
import jreader2.service.UserService;
import jreader2.web.model.CleanupPostsRequest;
import jreader2.web.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CleanupTaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final PostCleanupService postCleanupService;

    @PostMapping("/tasks/cleanup/all")
    public void cleanupPostsForAllUsers() {
        log.info("Cleanup of posts triggered for all users.");
        userService.list(Role.USER).forEach(user -> {
            log.info("Triggering cleanup of posts for user: {}", user.getEmail());
            taskService.enqueueToCleanupPosts(user.getEmail());
        });
    }

    @PostMapping("/tasks/cleanup/user")
    public void cleanupPostsForUser(@RequestBody CleanupPostsRequest request) {
        log.info("Cleanup of posts triggered for user: {}", request.getEmail());
        postCleanupService.cleanupFor(request.getEmail());
    }

}
