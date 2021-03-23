import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import { PostStore } from 'src/app/store/post.store';
import { PostFilter } from 'src/app/model/postfilter';
import { Post } from 'src/app/model/post';

@Component({
    selector: 'app-posts',
    templateUrl: './posts.component.html',
    styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

    store: PostStore;

    groupId: string;
    subscriptionId: string;
    selection: string;
    sort: string;

    constructor(private route: ActivatedRoute, private service: PostService, store: PostStore) {
        this.store = store;
    }

    ngOnInit(): void {
        this.sort = this.route.snapshot.queryParamMap.get('sort');
        if (!this.sort) {
            this.sort = 'asc';
        }

        this.selection = this.route.snapshot.queryParamMap.get('selection');
        if (!this.selection) {
            this.selection = 'unread';
        }

        this.route.paramMap.subscribe(params => {
            this.groupId = params.get('groupId');
            this.subscriptionId = params.get('subscriptionId');
            this.refreshPosts();

        });
        this.route.queryParamMap.subscribe(params => {
            const oldSelection = this.selection;
            const oldSort = this.sort;
            this.selection = params.get('selection') === 'all' ? 'all' : 'unread';
            this.sort = params.get('sort') === 'desc' ? 'desc' : 'asc';

            if (oldSelection !== this.selection || oldSort !== this.sort) {
                this.refreshPosts();
            }
        });
    }

    refreshPosts(): void {
        const filter = new PostFilter(this.groupId ? parseInt(this.groupId, 10) : null,
            this.subscriptionId ? parseInt(this.subscriptionId, 10) : null,
            this.selection, this.sort);
        this.service.load(filter);
    }

    read(post: Post): void {
        if (!post.read) {
            this.service.read(post);
        }
    }

    bookmark(post: Post): void {
        if (!post.bookmarked) {
            this.service.bookmark(post, true);
        }
    }

    removeBookmark(post: Post): void {
        if (post.bookmarked) {
            this.service.bookmark(post, false);
        }
    }

}
