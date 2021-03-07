import { Component, OnInit, Inject } from '@angular/core';
import { GroupService } from 'src/app/services/group.service';
import { FormControl, Validators, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-create-group',
    templateUrl: './create-group.component.html',
    styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit {

    form = new FormGroup({
        name: new FormControl('', [ Validators.required ])
    });

    constructor(private service: GroupService) { }

    ngOnInit(): void { }

    submit(): void {
        this.service.createGroup(this.form.value.name);
    }

}
