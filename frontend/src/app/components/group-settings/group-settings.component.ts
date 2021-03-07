import { Component, OnInit, Input } from '@angular/core';
import { Group } from 'src/app/model/group';
import { GroupService } from 'src/app/services/group.service';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmGroupDeletionComponent } from '../dialogs/confirm-group-deletion/confirm-group-deletion.component';

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

    constructor(public unsubscribeDialog: MatDialog, private service: GroupService) { }

    ngOnInit(): void { }

    confirmGroupDeletion(): void {
        const dialogRef = this.unsubscribeDialog.open(ConfirmGroupDeletionComponent, {
            data: { group: this.group }
        });

        dialogRef.afterClosed().subscribe(confirmed => {
            if (confirmed) {
                this.service.deleteGroup(this.group);
            }
        });
    }

}
