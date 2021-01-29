package jreader2.web.service;

import com.google.cloud.tasks.v2.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class TaskService {

    @RequiredArgsConstructor
    @Getter
    public enum TaskQueue {
        REFRESH_ALL_FEEDS("refresh-all-queue", "/tasks/refresh");

        private final String name;
        private final String uri;
    };

    public void enqueue(TaskQueue queue) {
        try (CloudTasksClient client = CloudTasksClient.create()) {
            client.createTask(
                    QueueName.of("uzonyib-jreader", "europe-west6", queue.getName()),
                    Task.newBuilder()
                            .setAppEngineHttpRequest(AppEngineHttpRequest.newBuilder()
                                    .setRelativeUri(queue.getUri())
                                    .setHttpMethod(HttpMethod.POST)
                                    .build())
                            .build());
            log.info("Task enqueued.");
        } catch (IOException e) {
            log.error("Failed to enqueue task.", e);
        }
    }

}
