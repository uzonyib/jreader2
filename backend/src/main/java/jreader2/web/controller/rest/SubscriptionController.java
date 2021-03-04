package jreader2.web.controller.rest;

import jreader2.domain.Subscription;
import jreader2.service.FeedService;
import jreader2.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/groups/{groupId}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final FeedService feedService;

    @PostMapping
    public Subscription create(OAuth2AuthenticationToken auth,
                               @PathVariable long groupId, @RequestBody Subscription subscription) {
        feedService.createIfNotExists(subscription.getUrl());
        return subscriptionService.subscribe(auth.getPrincipal().getAttribute("email"), groupId, subscription);
    }

}
