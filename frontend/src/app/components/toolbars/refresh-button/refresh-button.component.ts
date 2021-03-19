import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/post.service';

@Component({
    selector: 'app-refresh-button',
    templateUrl: './refresh-button.component.html',
    styleUrls: ['./refresh-button.component.css']
})
export class RefreshButtonComponent implements OnInit {

    loading = false;

    constructor(private service: PostService) {
        service.isLoading().subscribe(l => this.loading = l);
    }

    ngOnInit(): void { }

    refresh(): void {
        this.service.reload();
    }

}
