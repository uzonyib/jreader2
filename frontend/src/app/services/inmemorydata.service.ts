import { Injectable } from '@angular/core';
import { Group } from '../domain/group';

@Injectable({
    providedIn: 'root'
})
export class InMemoryDataService {

    groups: Group[] = [{
        id: 1,
        name: 'Backend',
        rank: 1,
        subscriptions: [{
            id: 1,
            name: 'Java'
        }, {
            id: 2,
            name: 'Spring'
        }]
    }, {
        id: 2,
        name: 'Frontend',
        rank: 2,
        subscriptions: [{
            id: 3,
            name: 'Angular'
        }]
    }];

    constructor() { }

    createGroup(group: Group): Group {
        console.log('Backend: create group');
        group.id = this.groups[this.groups.length - 1].id + 1;
        this.groups.push(group);
        return group;
    }

}
