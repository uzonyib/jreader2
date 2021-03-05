import { Component, OnInit, Input } from '@angular/core';
import { Subscription } from 'src/app/model/subscription';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmUnsubscribeComponent } from '../confirm-unsubscribe/confirm-unsubscribe.component';
import { GroupService } from 'src/app/services/group.service';

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

    constructor(public unsubscribeDialog: MatDialog, private service: GroupService) { }

    ngOnInit(): void { }

    confirmUnsubscribe(): void {
        const dialogRef = this.unsubscribeDialog.open(ConfirmUnsubscribeComponent, {
            data: { subscription: this.subscription }
        });

        dialogRef.afterClosed().subscribe(confirmed => {
            if (confirmed) {
                this.service.unsubscribe(this.subscription);
            }
        });
    }

}
