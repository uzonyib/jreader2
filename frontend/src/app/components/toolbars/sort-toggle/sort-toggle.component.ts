import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-sort-toggle',
    templateUrl: './sort-toggle.component.html',
    styleUrls: ['./sort-toggle.component.css']
})
export class SortToggleComponent implements OnInit {

    sort = 'asc';

    constructor(private router: Router, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.sort = this.route.snapshot.queryParamMap.get('sort') === 'desc' ? 'desc' : 'asc';
        this.toggle();
    }

    toggle(): void {
        this.router.navigate([], {
            relativeTo: this.route,
            queryParams: this.sort === 'desc' ? { sort: 'desc' } : { sort: 'asc' },
            queryParamsHandling: 'merge'
        });
    }

}
