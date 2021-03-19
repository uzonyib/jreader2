import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { InMemoryDataService } from '../services/inmemorydata.service';

@Injectable({
    providedIn: 'root'
})
export class MockRestInterceptor implements HttpInterceptor {

    constructor(private dataService: InMemoryDataService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (environment.production) {
            return next.handle(req);
        } else {
            return this.mockRequest(req);
        }
    }

    private mockRequest(req: HttpRequest<any>): Observable<HttpEvent<any>> {
        console.log(`Mocking request: ${req.method} ${req.url}`);
        if (req.method === 'GET' && req.url === '/rest/groups') {
            // get groups
            return this.createResponse(this.dataService.groups);
        } else if (req.method === 'POST' && req.url === '/rest/groups') {
            // create group
            const group = this.dataService.createGroup(req.body);
            return this.createResponse(group);
        } else if (req.method === 'DELETE' && req.url.match(/^\/rest\/groups\/[0-9]+$/)) {
            // delete group
            this.dataService.deleteGroup(parseInt(req.url.match(/[0-9]+/g)[0], 10));
            return this.createResponse('');
        } else if (req.method === 'POST' && req.url.match(/^\/rest\/groups\/[0-9]+\/subscriptions$/)) {
            // create subscription
            const subscription = this.dataService.subscribe(parseInt(req.url.match(/[0-9]+/g)[0], 10), req.body);
            return this.createResponse(subscription);
        } else if (req.method === 'DELETE' && req.url.match(/^\/rest\/groups\/[0-9]+\/subscriptions\/[0-9]+$/)) {
            // delete subscription
            const ids = req.url.match(/[0-9]+/g);
            const groupId = parseInt(ids[0], 10);
            const subscriptionId = parseInt(ids[1], 10);
            this.dataService.unsubscribe(groupId, subscriptionId);
            return this.createResponse('');
        } else if (req.method === 'GET' && req.url.match(/^\/rest\/posts(\?.*)?$/)) {
            // get posts
            const sort = req.url.match(/sort=([^&]*)/)[1];
            return this.createResponse(this.dataService.getPosts(sort));
        } else if (req.method === 'GET' && req.url.match(/^\/rest\/groups\/[0-9]+\/posts(\?.*)?$/)) {
            // get posts for group
            const groupId = parseInt(req.url.match(/[0-9]+/g)[0], 10);
            const sort = req.url.match(/sort=([^&]*)/)[1];
            return this.createResponse(this.dataService.getPostsForGroup(groupId, sort));
        } else if (req.method === 'GET' && req.url.match(/^\/rest\/groups\/[0-9]+\/subscriptions\/[0-9]+\/posts(\?.*)?$/)) {
            // get posts for subscription
            const ids = req.url.match(/[0-9]+/g);
            const groupId = parseInt(ids[0], 10);
            const subscriptionId = parseInt(ids[1], 10);
            const sort = req.url.match(/sort=([^&]*)/)[1];
            return this.createResponse(this.dataService.getPostsForSubscription(groupId, subscriptionId, sort));
        } else {
            throw new Error(`Mock handler not implemented for request ${req.method} ${req.url}`);
        }
    }

    private createResponse(body: any): Observable<HttpEvent<any>> {
        return of(new HttpResponse({ status: 200, body: JSON.parse(JSON.stringify(body)) }));
    }

}
