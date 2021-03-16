import { Component, OnInit } from '@angular/core';
import { PostStore } from 'src/app/store/post.store';

@Component({
    selector: 'app-reader-bar',
    templateUrl: './reader-bar.component.html',
    styleUrls: ['./reader-bar.component.css']
})
export class ReaderBarComponent implements OnInit {

    store: PostStore;

    constructor(store: PostStore) {
        this.store = store;
    }

    ngOnInit(): void { }

}
