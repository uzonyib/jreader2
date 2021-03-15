import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import { PostStore } from 'src/app/store/post.store';
import { PostFilter } from 'src/app/model/postfilter';

@Component({
    selector: 'app-posts',
    templateUrl: './posts.component.html',
    styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

    groupId: string;
    subscriptionId: string;
    store: PostStore;

    constructor(private route: ActivatedRoute, private service: PostService, store: PostStore) {
        this.store = store;
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe(params => {
            this.groupId = params.get('groupId');
            this.subscriptionId = params.get('subscriptionId');
            this.refreshPosts();
        });
    }

    refreshPosts(): void {
        const filter = new PostFilter(this.groupId ? parseInt(this.groupId) : null,
            this.subscriptionId ? parseInt(this.subscriptionId) : null);
        this.service.load(filter);
    }

}
