package jreader2.app.web.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CronController {

    @GetMapping("/cron/refresh")
    public void refresh() {
        log.info("Refresh triggered.");
    }

}
