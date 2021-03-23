import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import { SelectionService } from 'src/app/services/selection.service';

@Component({
    selector: 'app-selection-toggle',
    templateUrl: 'selection-toggle.component.html',
    styleUrls: ['selection-toggle.component.css']
})
export class SelectionToggleComponent implements OnInit {

    selection = 'unread';
    loading = false;

    constructor(private router: Router, private route: ActivatedRoute,
        private selectionService: SelectionService, service: PostService) {
        service.isLoading().subscribe(l => this.loading = l);
    }

    ngOnInit(): void {
        const param = this.route.snapshot.queryParamMap.get('selection');
        this.selection = this.selectionService.getOrDefault(param);
    }

    toggle(): void {
        this.router.navigate([], {
            relativeTo: this.route,
            queryParams: this.selectionService.isNonDefault(this.selection) ?
                { selection: this.selection } : { selection: null },
            queryParamsHandling: 'merge'
        });
    }

}
