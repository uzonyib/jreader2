import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { GroupService } from 'src/app/services/group.service';
import { GroupStore } from 'src/app/store/group.store';

@Component({
    selector: 'app-subscribe',
    templateUrl: './subscribe.component.html',
    styleUrls: ['./subscribe.component.css']
})
export class SubscribeComponent implements OnInit {

    store: GroupStore;

    form = new FormGroup({
        groupIndex: new FormControl('', [ Validators.required ]),
        url: new FormControl('', [
            Validators.required,
            Validators.pattern('((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%._\\+~#?&//=]*)')
        ]),
        name: new FormControl('', [ Validators.required ])
    });

    constructor(private service: GroupService, store: GroupStore) {
        this.store = store;
    }

    ngOnInit(): void { }

    submit(): void {
        this.service.subscribe(this.form.value.groupIndex, this.form.value.url, this.form.value.name);
    }

}
