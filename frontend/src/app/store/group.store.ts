import { Injectable } from '@angular/core';
import { Group } from '../model/group';
import { Subscription } from '../model/subscription';

@Injectable({
    providedIn: 'root'
})
export class GroupStore {

    groups: Group[] = [];

    constructor() { }

    public isEmpty(): boolean {
        return this.groups.length === 0;
    }

    public setGroups(groups: Group[]): void {
        groups.forEach(group => this.init(group));
        this.groups = groups;
    }

    public addGroup(group: Group): void {
        this.init(group);
        this.groups.push(group);
    }

    public addSubscription(groupIndex: number, subscription: Subscription): void {
        this.groups[groupIndex].subscriptions.push(subscription);
    }

    public removeSubscription(subscription: Subscription): void {
        const group = this.groups.find(g => g.id === subscription.groupId);
        const index = group.subscriptions.findIndex(s => s.id === subscription.id);
        group.subscriptions.splice(index, 1);
    }

    public getLastGroup(): Group {
        if (this.isEmpty()) {
            return undefined;
        }
        return this.groups[this.groups.length - 1];
    }

    private init(group: Group): void {
        group.subscriptions = group.subscriptions || [];
        group.collapsed = true;
    }

}
