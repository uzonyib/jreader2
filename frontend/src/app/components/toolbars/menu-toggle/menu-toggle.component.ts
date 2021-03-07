import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-menu-toggle',
    templateUrl: './menu-toggle.component.html',
    styleUrls: ['./menu-toggle.component.css']
})
export class MenuToggleComponent implements OnInit {

    menuOpened: string[] = [];

    constructor(private router: Router, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.menuOpened = this.route.snapshot.queryParamMap.get('menu') === 'on' ? ['on'] : [];
    }

    toggle(): void {
        this.router.navigate([], {
            relativeTo: this.route,
            queryParams: this.menuOpened.includes('on') ? { menu: 'on' } : { menu: null },
            queryParamsHandling: 'merge'
        });
    }

}
