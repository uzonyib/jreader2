import { Injectable } from '@angular/core';
import { Group } from '../domain/group';
import { Subscription } from '../domain/subscription';
import { Post } from '../domain/post';
import { PostUpdate } from '../domain/postupdate';

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

    posts: Post[] = [{
        groupId: 1,
        subscriptionId: 1,
        uri: 'java1',
        url: 'http://java.com/1',
        title: 'Java 1',
        description: 'Java 1 description line 1<br>line2',
        author: 'java author 1',
        publishDate: '2021-03-09T12:40:00Z',
        read: false,
        bookmarked: false
    }, {
        groupId: 1,
        subscriptionId: 1,
        uri: 'java2',
        url: 'http://java.com/2',
        title: 'Java 2',
        description: 'Java 2 description',
        author: '',
        publishDate: '2021-03-09T12:50:00Z',
        read: false,
        bookmarked: false
    }, {
        groupId: 1,
        subscriptionId: 2,
        uri: 'spring1',
        url: 'http://spring.com/1',
        title: 'Spring 1',
        description: 'Spring 1 description',
        author: 'spring author 1',
        publishDate: '2021-03-09T12:55:00Z',
        read: false,
        bookmarked: false
    }, {
        groupId: 2,
        subscriptionId: 3,
        uri: 'angular1',
        url: 'http://angular.com/1',
        title: 'Angular 1',
        description: 'Angular 1 description',
        author: 'angular author 1',
        publishDate: '2021-03-09T12:56:00Z',
        read: false,
        bookmarked: false
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

    getPosts(selection: string, sort: string): Post[] {
        return this.sort(this.filter(this.posts, selection), sort);
    }

    getPostsForGroup(groupId: number, selection: string, sort: string): Post[] {
        return this.sort(this.filter(this.posts.filter(post => post.groupId === groupId), selection), sort);
    }

    getPostsForSubscription(groupId: number, subscriptionId: number, selection: string, sort: string): Post[] {
        return this.sort(this.filter(
            this.posts.filter(post => post.groupId === groupId && post.subscriptionId === subscriptionId), selection),
            sort);
    }

    updatePosts(updates: PostUpdate[]): void {
        updates.forEach(update => {
            const post = this.posts.find(p => p.groupId === update.groupId && p.subscriptionId == update.subscriptionId
                && p.uri == update.uri);
            if (update.read !== undefined) {
                post.read = update.read;
            }
            if (update.bookmarked !== undefined) {
                post.bookmarked = update.bookmarked;
            }
        });
    }

    private filter(posts: Post[], selection: string): Post[] {
        if (selection === 'unread') {
            return posts.filter(post => !post.read);
        }
        if (selection === 'bookmarked') {
            return posts.filter(post => post.bookmarked);
        }
        return posts;
    }

    private sort(posts: Post[], sort: string): Post[] {
        const result = posts.sort(this.comparePosts);
        if (sort === 'desc') {
            return result.reverse();
        }
        return result;
    }

    private comparePosts(p1: Post, p2: Post): number {
        const d1 = new Date(p1.publishDate);
        const d2 = new Date(p2.publishDate);
        if (d1 > d2) {
            return 1;
        }
        if (d1 < d2) {
            return -1;
        }
        return 0;
    }

}
