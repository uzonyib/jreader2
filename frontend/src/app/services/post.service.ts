import { Injectable } from '@angular/core';
import { PostStore } from '../store/post.store';
import { HttpClient } from '@angular/common/http';
import { Post } from '../model/post';
import { PostFilter } from '../model/postfilter';
import { Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PostUpdate } from '../domain/postupdate';

@Injectable({
    providedIn: 'root'
})
export class PostService {

    private currentFilter: PostFilter;
    private loading = new Subject<boolean>();

    constructor(private http: HttpClient, private store: PostStore) { }

    load(filter: PostFilter): void {
        this.loading.next(true);
        this.store.setPosts([]);
        this.currentFilter = filter;

        if (filter.subscriptionId !== null) {
            this.http.get<Post[]>(`/rest/groups/${filter.groupId}/subscriptions/${filter.subscriptionId}/posts`
                    + `?sort=${filter.sort}&selection=${filter.selection}`)
                .subscribe(posts => this.storePosts(posts));
        } else if (filter.groupId !== null) {
            this.http.get<Post[]>(`/rest/groups/${filter.groupId}/posts?`
                    + `sort=${filter.sort}&selection=${filter.selection}`)
                .subscribe(posts => this.storePosts(posts));
        } else {
            this.http.get<Post[]>(`/rest/posts?sort=${filter.sort}&selection=${filter.selection}`).subscribe(posts => this.storePosts(posts));
        }
    }

    reload(): void {
        this.load(this.currentFilter);
    }

    isLoading(): Observable<boolean> {
        return this.loading;
    }

    read(post: Post): void {
        const update = new PostUpdate(post.groupId, post.subscriptionId, post.uri);
        update.read = true;
        this.http.post<void>('/rest/posts', [update]).subscribe(() => post.read = true );
    }

    readAll(): void {
        const updates: PostUpdate[] = [];
        this.store.posts.forEach(post => {
            if (!post.read) {
                const update = new PostUpdate(post.groupId, post.subscriptionId, post.uri);
                update.read = true;
                updates.push(update);
            }
        });
        if (updates.length > 0) {
            this.http.post<void>('/rest/posts', updates).subscribe(() => this.reload());
        }
    }

    bookmark(post: Post, bookmarked: boolean): void {
        post.bookmarked = undefined;
        const update = new PostUpdate(post.groupId, post.subscriptionId, post.uri);
        update.bookmarked = bookmarked;
        this.http.post<void>('/rest/posts', [update]).subscribe(() => post.bookmarked = bookmarked);
    }

    private storePosts(posts: Post[]): void {
        setTimeout(() => {
            this.store.setPosts(posts);
            this.loading.next(false);
        }, environment.production ? 0 : 1000);
    }

}
