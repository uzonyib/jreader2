import { Component, OnInit } from '@angular/core';
import { GroupStore } from 'src/app/store/group.store';

@Component({
    selector: 'app-overview',
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

    store: GroupStore;

    constructor(store: GroupStore) {
        this.store = store;
    }

    ngOnInit(): void { }

}
