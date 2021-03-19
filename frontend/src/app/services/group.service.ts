import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Group as GroupRequest } from '../domain/group';
import { Group } from '../model/group';
import { GroupStore } from '../store/group.store';
import { Subscription as SubscriptionRequest } from '../domain/subscription';
import { Subscription } from '../model/subscription';
import { PostStore } from '../store/post.store';

@Injectable({
    providedIn: 'root'
})
export class GroupService {

    constructor(private http: HttpClient, private store: GroupStore, private postStore: PostStore) { }

    initStore(): void {
        this.reloadStore();
    }

    createGroup(name: string): void {
        const group = new GroupRequest(name);
        group.rank = this.store.isEmpty() ? 1 : this.store.getLastGroup().rank + 1;
        this.http.post<Group>('/rest/groups', group).subscribe(result => this.store.addGroup(result));
    }

    deleteGroup(group: Group): void {
        this.http.delete(`/rest/groups/${group.id}`).subscribe(() => this.store.removeGroup(group));
    }

    subscribe(groupIndex: number, url: string, name: string): void {
        const group = this.store.groups[groupIndex]
        const subscription = new SubscriptionRequest(group.id, url, name);
        subscription.rank = group.subscriptions.length === 0 ? 1 : group.subscriptions[group.subscriptions.length - 1].rank + 1;
        this.http.post<Subscription>(`/rest/groups/${group.id}/subscriptions`, subscription)
            .subscribe(result => this.store.addSubscription(groupIndex, result));
    }

    unsubscribe(subscription: Subscription): void {
        this.http.delete(`/rest/groups/${subscription.groupId}/subscriptions/${subscription.id}`)
            .subscribe(() => this.store.removeSubscription(subscription));
    }

    private reloadStore(): void {
        this.http.get<Group[]>('/rest/groups').subscribe(groups => {
            this.store.setGroups(groups);
            this.postStore.refreshGroupReferences();
        });
    }

}
