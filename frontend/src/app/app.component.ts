import { Component } from '@angular/core';
import { GroupService } from './services/group.service';
import { environment } from 'src/environments/environment';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {

    constructor(groupService: GroupService) {
        console.log('Is prod: ' + environment.production);
        groupService.initStore();
    }

}
