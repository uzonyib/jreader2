import { Component, OnInit, Inject } from '@angular/core';
import { Group } from 'src/app/model/group';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface ConfirmGroupDeletionDialogData {
  group: Group;
}

@Component({
    selector: 'app-confirm-group-deletion',
    templateUrl: './confirm-group-deletion.component.html',
    styleUrls: ['./confirm-group-deletion.component.css']
})
export class ConfirmGroupDeletionComponent implements OnInit {

    constructor(@Inject(MAT_DIALOG_DATA) public data: ConfirmGroupDeletionDialogData) { }

    ngOnInit(): void { }

}
