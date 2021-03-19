import { Injectable } from '@angular/core';
import { Post } from '../model/post';
import { GroupStore } from './group.store';

@Injectable({
    providedIn: 'root'
})
export class PostStore {

    posts: Post[] = [];

    constructor(private groupStore: GroupStore) { }

    setPosts(posts: Post[]): void {
        posts.forEach(post => this.init(post));
        this.posts = posts;
    }

    refreshGroupReferences(): void {
        this.posts.forEach(post => this.init(post));
    }

    private init(post: Post): void {
        const group = this.groupStore.groups.find(g => g.id === post.groupId);
        if (group) {
            const subscription = group.subscriptions.find(s => s.id === post.subscriptionId);
            if (subscription) {
                post.subscriptionName = subscription.name;
            }
        }
    }

}
