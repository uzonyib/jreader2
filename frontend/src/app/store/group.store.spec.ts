import { TestBed } from '@angular/core/testing';

import { GroupStore } from './group.store';

describe('GroupStore', () => {
    let store: GroupStore;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        store = TestBed.inject(GroupStore);
    });

    it('should be created', () => {
        expect(store).toBeTruthy();
    });
});
