import { Component, OnInit, Input } from '@angular/core';
import { Group } from 'src/app/model/group';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmGroupDeletionComponent } from '../../dialogs/confirm-group-deletion/confirm-group-deletion.component';

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

    constructor(public unsubscribeDialog: MatDialog) { }

    ngOnInit(): void { }

    confirmGroupDeletion(): void {
        this.unsubscribeDialog.open(ConfirmGroupDeletionComponent, {
            data: { group: this.group }
        });
    }

}
