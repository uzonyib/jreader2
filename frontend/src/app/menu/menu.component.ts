import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  groups: object[] = [{
    "id": 1,
    "title": "Backend",
    "collapsed": true,
    "subscriptions": [{
      "id": 1,
      "title": "Java"
    }, {
      "id": 2,
      "title": "Spring"
    }]
  }, {
    "id": 2,
    "title": "Frontend",
    "collapsed": true,
    "subscriptions": [{
      "id": 3,
      "title": "Angular"
    }]
  }];

  constructor() { }

  ngOnInit(): void {
  }

}
