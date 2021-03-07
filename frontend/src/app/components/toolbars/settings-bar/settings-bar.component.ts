import { Component, OnInit } from '@angular/core';
import { GroupStore } from 'src/app/store/group.store';

@Component({
  selector: 'app-settings-bar',
  templateUrl: './settings-bar.component.html',
  styleUrls: ['./settings-bar.component.css']
})
export class SettingsBarComponent implements OnInit {

  constructor(public store: GroupStore) { }

  ngOnInit(): void { }

}
