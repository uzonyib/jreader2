import { Component, OnInit } from '@angular/core';
import { GroupService } from 'src/app/services/group.service';

@Component({
    selector: 'app-creategroup',
    templateUrl: './creategroup.component.html',
    styleUrls: ['./creategroup.component.css']
})
export class CreateGroupComponent implements OnInit {

    name = '';

    constructor(private service: GroupService) { }

    ngOnInit(): void { }

    submit(): void {
        this.service.createGroup(this.name);
        this.name = '';
    }

}
