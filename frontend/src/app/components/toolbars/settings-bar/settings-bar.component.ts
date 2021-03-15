import { Component, OnInit } from '@angular/core';
import { GroupStore } from 'src/app/store/group.store';
import { MatDialog } from '@angular/material/dialog';
import { CreateGroupComponent } from '../../dialogs/create-group/create-group.component';
import { SubscribeComponent } from '../../dialogs/subscribe/subscribe.component';

@Component({
    selector: 'app-settings-bar',
    templateUrl: './settings-bar.component.html',
    styleUrls: ['./settings-bar.component.css']
})
export class SettingsBarComponent implements OnInit {

    constructor(public groupCreationDialog: MatDialog, public subscribeDialog: MatDialog, public store: GroupStore) { }

    ngOnInit(): void { }

    openGroupCreationDialog(): void {
        this.groupCreationDialog.open(CreateGroupComponent, {
            data: {}
        });
    }

    openSubscribeDialog(): void {
        this.subscribeDialog.open(SubscribeComponent, {
            data: {}
        });
    }

}
