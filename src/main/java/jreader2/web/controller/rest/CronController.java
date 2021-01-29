package jreader2.web.controller.rest;

import jreader2.web.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static jreader2.web.service.TaskService.TaskQueue.REFRESH_ALL_FEEDS;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CronController {

    private final TaskService taskService;

    @GetMapping("/cron/refresh")
    public void refresh() {
        log.info("Triggering refresh for all feeds.");
        taskService.enqueue(REFRESH_ALL_FEEDS);
    }

}
