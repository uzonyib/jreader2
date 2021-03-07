import { Component, OnInit } from '@angular/core';
import { GroupStore } from 'src/app/store/group.store';

@Component({
    selector: 'app-settings',
    templateUrl: './settings.component.html',
    styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

    store: GroupStore;

    constructor(store: GroupStore) {
        this.store = store;
    }

    ngOnInit(): void { }

}
