package jreader2.web.controller.rest;

import jreader2.service.CronService;
import jreader2.service.RssService;
import jreader2.web.model.RefreshRequest;
import jreader2.web.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final CronService cronService;
    private final RssService rssService;

    @PostMapping("/tasks/refresh/all")
    public void refreshAllFeeds() {
        log.info("Refresh triggered for all feeds.");
        cronService.listAll().forEach(url -> {
            log.info("Triggering refresh for feed: {}", url);
            taskService.enqueueToRefresh(url);
        });
    }

    @PostMapping("/tasks/refresh/feed")
    public void refreshFeed(@RequestBody RefreshRequest request) {
        log.info("Refresh triggered for feed: {}", request.getUrl());
        rssService.fetch(request.getUrl());
    }

}
