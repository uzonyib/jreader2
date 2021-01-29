package jreader2.web.controller.rest;

import jreader2.service.CronService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final CronService cronService;

    @PostMapping("/tasks/refresh")
    public void refresh() {
        log.info("Refresh triggered for all feeds: {}", cronService.listAll());
    }

}
