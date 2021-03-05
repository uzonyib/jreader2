import { Component, OnInit, Input } from '@angular/core';
import { Subscription } from 'src/app/model/subscription';

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

    constructor() { }

    ngOnInit(): void { }

}
