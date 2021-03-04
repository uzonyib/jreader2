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
        console.log('Mocking request: ' + req.method + ' ' + req.url);
        if (req.method === 'GET' && req.url === '/rest/groups') {
            return this.createResponse(this.dataService.groups);
        } else if (req.method === 'POST' && req.url === '/rest/groups') {
            const group = this.dataService.createGroup(req.body);
            return this.createResponse(group);
        } else if (req.method === 'POST' && req.url.match(/\/rest\/groups\/[0-9]+\/subscriptions/)) {
            const subscription = this.dataService.subscribe(parseInt(req.url.match(/[0-9]+/)[0], 10), req.body);
            return this.createResponse(subscription);
        } else {
            throw new Error('mock handler not implemented for request ' + req.url);
        }
    }

    private createResponse(body: any): Observable<HttpEvent<any>> {
        return of(new HttpResponse({ status: 200, body: JSON.parse(JSON.stringify(body)) }));
    }

}
