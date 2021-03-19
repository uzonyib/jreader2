import { Component, OnInit } from '@angular/core';
import { PostStore } from 'src/app/store/post.store';
import { PostService } from 'src/app/services/post.service';

@Component({
    selector: 'app-posts-status',
    templateUrl: './posts-status.component.html',
    styleUrls: ['./posts-status.component.css']
})
export class PostsStatusComponent implements OnInit {

    store: PostStore;
    loading = false;

    constructor(store: PostStore, service: PostService) {
        this.store = store;
        service.isLoading().subscribe(l => this.loading = l);
    }

    ngOnInit(): void { }

}
