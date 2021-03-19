import { Injectable } from '@angular/core';
import { PostStore } from '../store/post.store';
import { HttpClient } from '@angular/common/http';
import { Post } from '../model/post';
import { PostFilter } from '../model/postfilter';

@Injectable({
    providedIn: 'root'
})
export class PostService {

    constructor(private http: HttpClient, private store: PostStore) { }

    load(filter: PostFilter): void {
        if (filter.subscriptionId !== null) {
            this.http.get<Post[]>(`/rest/groups/${filter.groupId}/subscriptions/${filter.subscriptionId}/posts?sort=${filter.sort}`)
                .subscribe(posts => this.store.setPosts(posts));
        } else if (filter.groupId !== null) {
            this.http.get<Post[]>(`/rest/groups/${filter.groupId}/posts?sort=${filter.sort}`)
                .subscribe(posts => this.store.setPosts(posts));
        } else {
            this.http.get<Post[]>(`/rest/posts?sort=${filter.sort}`).subscribe(posts => this.store.setPosts(posts));
        }
    }

}
