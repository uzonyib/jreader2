import { Injectable } from '@angular/core';
import { Group } from '../domain/group';
import { Subscription } from '../domain/subscription';

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
            groupId: 1,
            url: 'https://java.com/rss',
            name: 'Java',
            rank: 1
        }, {
            id: 2,
            groupId: 1,
            url: 'https://spring.com/rss',
            name: 'Spring',
            rank: 2
        }]
    }, {
        id: 2,
        name: 'Frontend',
        rank: 2,
        subscriptions: [{
            id: 3,
            groupId: 2,
            url: 'https://angular.com/rss',
            name: 'Angular',
            rank: 1
        }]
    }];

    constructor() { }

    createGroup(group: Group): Group {
        console.log('Backend: create group');
        group.id = this.groups[this.groups.length - 1].id + 1;
        group.subscriptions = [];
        this.groups.push(group);
        return group;
    }

    deleteGroup(groupId: number): void {
        console.log(`Backend: delete group ${groupId}`);
        const index = this.groups.findIndex(g => g.id === groupId);
        this.groups.splice(index, 1);
    }

    subscribe(groupId: number, subscription: Subscription): Subscription {
        console.log(`Backend: create subscription for group ${groupId}`);
        const group = this.groups.find(g => g.id === groupId);
        const empty = group.subscriptions.length === 0;
        subscription.id = empty ? 1 : group.subscriptions[group.subscriptions.length - 1].id + 1;
        group.subscriptions.push(subscription);
        return subscription;
    }

    unsubscribe(groupId: number, subscriptionId: number): void {
        console.log(`Backend: delete subscription ${subscriptionId} from group ${groupId}`);
        const group = this.groups.find(g => g.id === groupId);
        const index = group.subscriptions.findIndex(s => s.id === subscriptionId);
        group.subscriptions.splice(index, 1);
    }

}
