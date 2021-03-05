import { Component, OnInit, Input } from '@angular/core';
import { Group } from 'src/app/model/group';

@Component({
    selector: 'app-group-settings',
    templateUrl: './group-settings.component.html',
    styleUrls: ['./group-settings.component.css']
})
export class GroupSettingsComponent implements OnInit {

    @Input()
    group: Group;
    @Input()
    first: boolean;
    @Input()
    last: boolean;

    constructor() { }

    ngOnInit(): void { }

}
