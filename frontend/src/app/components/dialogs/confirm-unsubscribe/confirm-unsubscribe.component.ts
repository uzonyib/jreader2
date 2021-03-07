import { Component, OnInit, Inject } from '@angular/core';
import { Subscription } from 'src/app/model/subscription';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

export interface ConfirmUnsubscribeDialogData {
  subscription: Subscription;
}

@Component({
    selector: 'app-confirm-unsubscribe',
    templateUrl: './confirm-unsubscribe.component.html',
    styleUrls: ['./confirm-unsubscribe.component.css']
})
export class ConfirmUnsubscribeComponent implements OnInit {

    constructor(@Inject(MAT_DIALOG_DATA) public data: ConfirmUnsubscribeDialogData) { }

    ngOnInit(): void { }

}
