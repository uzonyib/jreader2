import { Component, OnInit } from '@angular/core';
import { GroupService } from 'src/app/services/group.service';
import { FormControl, Validators, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-creategroup',
    templateUrl: './creategroup.component.html',
    styleUrls: ['./creategroup.component.css']
})
export class CreateGroupComponent implements OnInit {

    form = new FormGroup({
        name: new FormControl('', [ Validators.required ])
    });

    constructor(private service: GroupService) { }

    ngOnInit(): void { }

    submit(): void {
        this.service.createGroup(this.form.value.name);
        this.form.setValue({ name: '' });
    }

}
