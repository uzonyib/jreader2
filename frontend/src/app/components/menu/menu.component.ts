import { Component, OnInit } from '@angular/core';
import { GroupStore } from '../../store/group.store';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

    store: GroupStore;

    constructor(store: GroupStore) {
        this.store = store;
    }

    ngOnInit(): void { }

}
