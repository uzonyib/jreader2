import { TestBed } from '@angular/core/testing';

import { MockRestInterceptor } from './mockrestinterceptor';

describe('MockRestInterceptor', () => {
    let interceptor: MockRestInterceptor;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        interceptor = TestBed.inject(MockRestInterceptor);
    });

    it('should be created', () => {
        expect(interceptor).toBeTruthy();
    });
});
