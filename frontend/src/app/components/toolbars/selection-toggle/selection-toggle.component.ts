import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
    selector: 'app-selection-toggle',
    templateUrl: 'selection-toggle.component.html',
    styleUrls: ['selection-toggle.component.css']
})
export class SelectionToggleComponent implements OnInit {

    selection = 'unread';
    loading = false;

    constructor(private router: Router, private route: ActivatedRoute, service: PostService) {
        service.isLoading().subscribe(l => this.loading = l);
    }

    ngOnInit(): void {
        this.selection = this.route.snapshot.queryParamMap.get('selection') === 'all' ? 'all' : 'unread';
    }

    toggle(): void {
        this.router.navigate([], {
            relativeTo: this.route,
            queryParams: this.selection === 'all' ? { selection: 'all' } : { selection: null },
            queryParamsHandling: 'merge'
        });
    }

}
