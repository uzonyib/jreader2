import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';

@Component({
    selector: 'app-sort-toggle',
    templateUrl: './sort-toggle.component.html',
    styleUrls: ['./sort-toggle.component.css']
})
export class SortToggleComponent implements OnInit {

    sort = 'asc';
    loading = false;

    constructor(private router: Router, private route: ActivatedRoute, service: PostService) {
        service.isLoading().subscribe(l => this.loading = l);
    }

    ngOnInit(): void {
        this.sort = this.route.snapshot.queryParamMap.get('sort') === 'desc' ? 'desc' : 'asc';
    }

    toggle(): void {
        this.router.navigate([], {
            relativeTo: this.route,
            queryParams: this.sort === 'desc' ? { sort: 'desc' } : { sort: null },
            queryParamsHandling: 'merge'
        });
    }

}
