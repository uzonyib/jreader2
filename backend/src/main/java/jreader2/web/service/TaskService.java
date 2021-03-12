package jreader2.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.tasks.v2.*;
import com.google.protobuf.ByteString;
import jreader2.web.model.CleanupPostsRequest;
import jreader2.web.model.RefreshRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final ObjectMapper mapper;

    @RequiredArgsConstructor
    @Getter
    public enum TaskQueue {
        REFRESH_ALL_FEEDS("refresh-all-queue", "/tasks/refresh/all"),
        REFRESH_FEED("refresh-feed-queue", "/tasks/refresh/feed"),
        CLEANUP_ALL_POSTS("cleanup-posts-queue", "/tasks/cleanup/all"),
        CLEANUP_POSTS_FOR_USER("cleanup-posts-for-user-queue", "/tasks/cleanup/user");

        private final String name;

        private final String uri;


    };
    public void enqueueToRefreshAll() {
        enqueue(TaskQueue.REFRESH_ALL_FEEDS, Optional.empty());
    }
    public void enqueueToRefresh(String url) {
        enqueue(TaskQueue.REFRESH_FEED, Optional.of(new RefreshRequest(url)));
    }

    public void enqueueToCleanupPosts() {
        enqueue(TaskQueue.CLEANUP_ALL_POSTS, Optional.empty());
    }

    public void enqueueToCleanupPosts(String email) {
        enqueue(TaskQueue.CLEANUP_POSTS_FOR_USER, Optional.of(new CleanupPostsRequest(email)));
    }

    private void enqueue(TaskQueue queue, Optional<Object> payload) {
        try (CloudTasksClient client = CloudTasksClient.create()) {
            client.createTask(
                    QueueName.of("uzonyib-jreader", "europe-west6", queue.getName()),
                    Task.newBuilder()
                            .setAppEngineHttpRequest(createRequest(queue.getUri(), payload))
                            .build());
            log.info("Task enqueued.");
        } catch (IOException e) {
            log.error("Failed to enqueue task.", e);
        }
    }

    private AppEngineHttpRequest createRequest(String uri, Optional<Object> payload) throws JsonProcessingException {
        AppEngineHttpRequest.Builder builder = AppEngineHttpRequest.newBuilder()
                .setRelativeUri(uri)
                .setHttpMethod(HttpMethod.POST);
        if (payload.isPresent()) {
                builder
                        .putHeaders(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ByteString.copyFromUtf8(mapper.writeValueAsString(payload)));
        }
        return builder.build();
    }

}
