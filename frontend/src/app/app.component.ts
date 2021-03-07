import { Component, OnInit } from '@angular/core';
import { GroupService } from './services/group.service';
import { environment } from 'src/environments/environment';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    public menuOpened: boolean;

    constructor(groupService: GroupService, private route: ActivatedRoute) {
        console.log('Is prod: ' + environment.production);
        groupService.initStore();
    }

    ngOnInit(): void {
        this.route.queryParamMap.subscribe(params => {
            this.menuOpened = params.get('menu') === 'on';
        });
    }

}
