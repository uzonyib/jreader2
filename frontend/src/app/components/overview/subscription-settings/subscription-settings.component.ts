import { Component, OnInit, Input } from '@angular/core';
import { Subscription } from 'src/app/model/subscription';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmUnsubscribeComponent } from '../../dialogs/confirm-unsubscribe/confirm-unsubscribe.component';

@Component({
    selector: 'app-subscription-settings',
    templateUrl: './subscription-settings.component.html',
    styleUrls: ['./subscription-settings.component.css']
})
export class SubscriptionSettingsComponent implements OnInit {

    @Input()
    subscription: Subscription;
    @Input()
    first: boolean;
    @Input()
    last: boolean;

    constructor(public unsubscribeDialog: MatDialog) { }

    ngOnInit(): void { }

    confirmUnsubscribe(): void {
        this.unsubscribeDialog.open(ConfirmUnsubscribeComponent, {
            data: { subscription: this.subscription }
        });
    }

}
