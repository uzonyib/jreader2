import { Component } from '@angular/core';
import { PostService } from 'src/app/services/post.service';

@Component({
    selector: 'app-read-all-button',
    templateUrl: 'read-all-button.component.html',
    styleUrls: ['read-all-button.component.css']
})
export class ReadAllButtonComponent {

    loading = false;

    constructor(private service: PostService) {
        service.isLoading().subscribe(l => this.loading = l);
    }

    readAll(): void {
        this.service.readAll();
    }

}
